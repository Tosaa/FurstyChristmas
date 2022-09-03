package redtoss.example.furstychristmas.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import redtoss.example.furstychristmas.util.DateUtil
import java.time.LocalDate

@Composable
fun DebugScreen(
    context: Context,
    onClose: () -> Unit,
    currentDebugDate: LocalDate,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        val debugDate = remember { mutableStateOf(currentDebugDate) }
        val datePickerDialog = remember {
            DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, day: Int ->
                debugDate.value = LocalDate.of(year, month + 1, day)
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
        Row(Modifier.fillMaxWidth()) {
            Button(onClick = {
                DateUtil.setDevDay(debugDate.value)
                onClose()
            }) {
                Text("Apply")
            }
        }
    }
}