package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.get
import redtoss.example.furstychristmas.BuildConfig
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.theme.DayLocked
import redtoss.example.furstychristmas.ui.theme.DayNotCompleted
import redtoss.example.furstychristmas.ui.viewmodel.InfoViewModel
import redtoss.example.furstychristmas.ui.viewmodel.OverviewViewModel
import redtoss.example.furstychristmas.util.DateUtil
import java.time.LocalDate

@Composable
fun OverviewScreen(
    overviewViewModel: OverviewViewModel = get(),
    calendarViewModel: InfoViewModel = get(),
    onNavigateToCard: (LocalDate) -> Unit,
    onNavigateToDebug: () -> Unit = {},
) {
    val days = overviewViewModel.allCalendarCards.collectAsState(initial = emptyList())
    val today = DateUtil.today()
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Overview")
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(days.value) { _, item ->
                Card(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val daysColor = remember {
                        when {
                            item.day.isAfter(today) -> DayLocked
                            item.isDone -> DayCompleted
                            !item.isDone -> DayNotCompleted
                            else -> DayLocked
                        }
                    }
                    Button(
                        onClick = {
                            calendarViewModel.date = item.day
                            onNavigateToCard(item.day)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = daysColor
                        ),
                        enabled = item.day.isBefore(today),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${item.day.dayOfMonth}",
                                modifier = Modifier.background(Color.Transparent),
                                fontSize = 28.sp
                            )
                        }
                    }
                }
            }
        }
        if (BuildConfig.DEBUG) {
            Button(onClick = { onNavigateToDebug() }) {
                Text("DEBUG")
            }
        }
    }
}