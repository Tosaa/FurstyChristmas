package redtoss.example.furstychristmas.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.DatePicker
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get
import org.koin.androidx.compose.inject
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.util.DateUtil
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DebugScreen(
    context: Context,
) {
    val dayCompletionStatusUseCase: DayCompletionStatusUseCase = get()
    val preferences: SharedPreferences by inject()
    val debugScope = rememberCoroutineScope()

    val days = dayCompletionStatusUseCase.getDaysToComplete.asFlow().collectAsState(initial = emptyList())
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
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                datePickerDialog.show()
            }) {
                Text("Change Debug Date")
            }
            Text(text = debugDate.value.toString())
        }
        val debugScope = rememberCoroutineScope()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .scrollable(rememberScrollState(), Orientation.Vertical)
                .fillMaxWidth()
        ) {
            days.value.forEach {
                item {
                    Row(verticalAlignment = CenterVertically) {
                        Text(it.day.toString())
                        Switch(checked = it.isDone, onCheckedChange = { isDone ->
                            debugScope.launch {
                                if (isDone) {
                                    dayCompletionStatusUseCase.markDayAsDone(it.day)
                                } else {
                                    dayCompletionStatusUseCase.markDayAsNotDone(it.day)
                                }
                            }
                        })
                    }
                }
            }
        }

    }
}