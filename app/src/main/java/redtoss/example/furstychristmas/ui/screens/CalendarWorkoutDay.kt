@file:OptIn(ExperimentalMaterialApi::class)

package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.get
import redtoss.example.furstychristmas.domain.workout.model.Drill
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.model.Repetition
import redtoss.example.furstychristmas.model.RepetitionPerSide
import redtoss.example.furstychristmas.model.Seconds
import redtoss.example.furstychristmas.model.SecondsPerSide
import redtoss.example.furstychristmas.ui.MyAppBar
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.theme.FurstyChrismasTheme
import redtoss.example.furstychristmas.ui.viewmodel.WorkoutViewModel
import java.time.LocalDate

@Composable
fun CalendarWorkoutDay(
    viewmodel: WorkoutViewModel = get(),
    day: LocalDate,
    onClose: () -> Unit,
    onExerciseClicked: (String) -> Unit,
    onDayCompletedClicked: (LocalDate) -> Unit
) {
    viewmodel.date = day
    val motto = viewmodel.motto.collectAsState(initial = "")
    val exercises = viewmodel.exercises.collectAsState(initial = emptyList())
    val isDayCompleted = viewmodel.isDone.collectAsState(initial = false)
    val rounds = viewmodel.rounds.collectAsState(initial = 0)
    CalendarWorkoutContent(day, isDayCompleted, motto, exercises, onExerciseClicked, rounds) { day ->
        viewmodel.completeDay()
        onDayCompletedClicked(day)
        onClose()
    }
}

@Composable
private fun CalendarWorkoutContent(
    date: LocalDate,
    isDayCompleted: State<Boolean>,
    motto: State<String>,
    exercises: State<List<Drill>>,
    onExerciseClicked: (String) -> Unit,
    rounds: State<Int>,
    onDayCompletedClicked: (LocalDate) -> Unit
) {
    Surface {
        Card(modifier = Modifier.padding(8.dp)) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WorkoutTitle(title = motto)
                Exercises(exercises, onExerciseClicked)
                Rounds(rounds)
                Spacer(modifier = Modifier.weight(1f))
                DoneButton(isDayCompleted) {
                    onDayCompletedClicked(date)
                }
            }
        }
    }
}

@Composable
private fun WorkoutTitle(title: State<String>) {
    Text(
        text = title.value,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 26.sp,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
private fun Exercises(
    exercises: State<List<Drill>>,
    onExerciseClicked: (String) -> Unit
) {
    LazyColumn() {
        items(exercises.value) { drill ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onExerciseClicked(drill.exercise.exerciseId)
                    }
            ) {
                Text(
                    text = drill.exercise.exerciseName,
                    modifier = Modifier
                        .padding(6.dp)
                        .width(200.dp),
                    fontSize = 20.sp,
                    maxLines = 2,
                    softWrap = true
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = drill.repetition.formattedString().toString(),
                    modifier = Modifier.padding(6.dp),
                    fontSize = 20.sp,
                    softWrap = false
                )
            }
        }
    }
}

@Composable
private fun Rounds(rounds: State<Int>) {
    Text(text = "${rounds.value}-Runden", fontSize = 20.sp)
}


@Composable
private fun DoneButton(isDayCompleted: State<Boolean>, onDoneClicked: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isDayCompleted.value) {
            Button(
                onClick = onDoneClicked,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DayCompleted,
                    contentColor = MaterialTheme.colors.onPrimary
                )
            ) {
                Text(text = "Done")
            }
        }
    }
}


@Preview
@Composable
fun Preview() {
    FurstyChrismasTheme(true) {
        Column {
            MyAppBar(onBackIconClicked = { }, onInfoClicked = { }, onEditClicked = { }, onSportClicked = {})
            CalendarWorkoutContent(
                date = LocalDate.of(2021, 12, 16),
                isDayCompleted = mutableStateOf(false),
                motto = mutableStateOf("Motto of the Day"),
                exercises = mutableStateOf(
                    listOf(
                        Drill(Repetition(15), Exercise("CUSTOM_ID_0", "First Exercise", listOf(), "StartPosition", "Execution")),
                        Drill(RepetitionPerSide(29), Exercise("CUSTOM_ID_1", "Second Exercise", listOf(), "StartPosition", "Execution")),
                        Drill(Seconds(30), Exercise("CUSTOM_ID_2", "Third Exercise which is very long", listOf(), "StartPosition", "Execution")),
                        Drill(SecondsPerSide(60), Exercise("CUSTOM_ID_3", "Fourth Exercise", listOf(), "StartPosition", "Execution")),
                        Drill(Repetition(99), Exercise("CUSTOM_ID_0", "First Exercise", listOf(), "StartPosition", "Execution")),
                        Drill(Seconds(99), Exercise("CUSTOM_ID_BREAK", "Pause", listOf(), "", "")),
                    )
                ),
                onExerciseClicked = {},
                rounds = mutableStateOf(6),
                onDayCompletedClicked = {}
            )
        }
    }
}
