package redtoss.example.furstychristmas.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.DatePicker
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.compose.koinInject
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.theme.DayLocked
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.DateUtil.season
import timber.log.Timber

@Composable
fun DebugScreen(
    context: Context,
) {
    val dayCompletionStatusUseCase: DayCompletionStatusUseCase = koinInject()
    val infoUseCase: LoadInfoUseCase = koinInject()
    val workoutUseCase: LoadWorkoutUseCase = koinInject()
    val preferences: SharedPreferences = koinInject()
    val debugScope = rememberCoroutineScope()
    val activeSeason = remember { DateUtil.today().season() }
    val days = remember { dayCompletionStatusUseCase.getDaysToCompleteForSeason(activeSeason).asFlow() }.collectAsState(initial = emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        val debugDate = remember { mutableStateOf(DateUtil.today()) }
        val datePickerDialog = remember {
            DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, day: Int ->
                debugDate.value = LocalDate.of(year, month + 1, day)
                DateUtil.setDevDay(debugDate.value)
                debugScope.launch { preferences.edit().putString("developer_date", debugDate.value.format(DateTimeFormatter.ISO_LOCAL_DATE)).apply() }
            }, debugDate.value.year, debugDate.value.monthValue - 1, debugDate.value.dayOfMonth)
        }
        Text("Debug Screen", fontSize = 26.sp)
        Text("Season: $activeSeason", Modifier.fillMaxWidth())
        Row(Modifier.fillMaxWidth(), verticalAlignment = CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = { datePickerDialog.show() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DayCompleted
                )
            ) {
                Text("Change Debug Date")
            }
            Text(text = debugDate.value.toString())
        }
        Button(
            onClick = {
                (1..23).forEach {
                    val date = LocalDate.of(activeSeason, Month.DECEMBER, it)
                    debugScope.launch {
                        runBlocking {
                            infoUseCase.getInfoAtDay(date)?.let { info -> Timber.i("${info.date} : INFO: ${info.title}") }
                            workoutUseCase.getWorkoutAtDay(date)?.let { workout -> Timber.i("${workout.date} : Workout : ${workout.motto}") }
                        }
                    }
                }

            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DayCompleted
            )
        ) {
            Text("Log all days")
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .fillMaxWidth()
        ) {
            items(items = days.value) {
                val isDayCompleted = remember { mutableStateOf(it.isDone) }
                Row(verticalAlignment = CenterVertically) {
                    Checkbox(
                        checked = isDayCompleted.value,
                        onCheckedChange = { dayCompleted ->
                            debugScope.launch {
                                isDayCompleted.value = dayCompleted
                                if (dayCompleted) {
                                    dayCompletionStatusUseCase.markDayAsDone(it.day)
                                } else {
                                    dayCompletionStatusUseCase.markDayAsNotDone(it.day)
                                }
                            }
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DayCompleted,
                            uncheckedColor = DayLocked,
                        )
                    )
                    Text(it.day.dayOfMonth.toString())
                }
            }
        }
    }
}
