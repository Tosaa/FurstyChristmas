package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.theme.DayNotCompleted
import java.time.LocalDate

@Composable
fun CalendarHeader(date: LocalDate, isDone: Boolean) {
    val color = if (isDone) DayCompleted else DayNotCompleted
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color, RoundedCornerShape(5.dp))
                .size(40.dp, 40.dp)
        ) {
            Text(text = date.dayOfMonth.toString(), fontSize = 28.sp)
        }
    }
}