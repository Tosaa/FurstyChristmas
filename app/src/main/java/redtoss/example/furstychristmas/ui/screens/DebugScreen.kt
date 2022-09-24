package redtoss.example.furstychristmas.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.DatePicker
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.theme.DayLocked
import redtoss.example.furstychristmas.util.DateUtil
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DebugScreen(
    context: Context,
) {
    val dayCompletionStatusUseCase: DayCompletionStatusUseCase = get()
    val preferences: SharedPreferences = get()
    val debugScope = rememberCoroutineScope()
    val days = dayCompletionStatusUseCase.getDaysToComplete.value ?: emptyList()

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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .fillMaxWidth()
        ) {
            items(items = days) {
                val isDayCompleted = remember { mutableStateOf(it.isDone) }
                Row(verticalAlignment = CenterVertically) {
                    Text(it.day.toString())
                    Switch(
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
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = DayCompleted,
                            uncheckedThumbColor = DayLocked,
                        )
                    )
                }
            }
        }
    }
}
