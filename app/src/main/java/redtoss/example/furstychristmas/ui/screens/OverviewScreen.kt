package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.domain.day.model.isAvailableOn
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.theme.DayLocked
import redtoss.example.furstychristmas.ui.theme.DayNotCompleted
import redtoss.example.furstychristmas.ui.viewmodel.InfoViewModel
import redtoss.example.furstychristmas.ui.viewmodel.OverviewViewModel
import redtoss.example.furstychristmas.util.DateUtil
import java.time.LocalDate

@Composable
fun OverviewScreen(
    overviewViewModel: OverviewViewModel = koinViewModel(),
    calendarViewModel: InfoViewModel = koinViewModel(),
    onNavigateToCard: (LocalDate) -> Unit,
) {
    val days = overviewViewModel.allCalendarCards.collectAsState(initial = emptyList())
    val today = DateUtil.today()
    AllCards(days, today) { day ->
        onNavigateToCard(day.day)
    }
}

@Composable
fun AllCards(days: State<List<Day>>, today: LocalDate, onSingleDayClicked: (Day) -> Unit) {
    Surface {
        Column(modifier = Modifier.padding(8.dp)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(days.value) { _, item ->
                    CalendarDay(item = item, today = today, onClick = onSingleDayClicked)
                }
            }
        }
    }
}

@Composable
fun CalendarDay(item: Day, today: LocalDate, onClick: (Day) -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
    ) {
        val daysColor = when {
            !item.isAvailableOn(today) -> DayLocked
            item.isDone -> DayCompleted
            !item.isDone -> DayNotCompleted
            else -> DayLocked
        }
        Button(
            onClick = { onClick(item) },
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = daysColor,
                contentColor = MaterialTheme.colors.onPrimary,
                disabledBackgroundColor = daysColor,
                disabledContentColor = MaterialTheme.colors.onPrimary
            ),
            enabled = item.isAvailableOn(today),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
            ) {
                if (!item.isAvailableOn(today))
                    Image(
                        Icons.Default.Lock,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                        modifier = Modifier.fillMaxSize()
                    )
                else
                    Text(
                        text = "${item.day.dayOfMonth}",
                        fontSize = 28.sp
                    )
            }
        }
    }
}

/*
@Preview
@Composable
fun Preview() {
    FurstyChrismasTheme(false) {
        Column {
            MyAppBar(onBackIconClicked = { }, onInfoClicked = { }, onEditClicked = { })
            val numberedList = mutableStateOf(IntRange(1, 24).toList().shuffled(Random(2412)).map { day ->
                Day(LocalDate.of(2021, 12, day), Random().nextBoolean())
            })
            AllCards(days = numberedList, today = LocalDate.of(2021, 12, 16), onSingleDayClicked = {})

        }
    }
}
*/