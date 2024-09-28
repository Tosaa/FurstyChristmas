@file:OptIn(ExperimentalMaterialApi::class)

package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import org.koin.androidx.compose.koinViewModel
import redtoss.example.furstychristmas.domain.workout.model.Drill
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.viewmodel.WorkoutViewModel

@Composable
fun CalendarWorkoutDay(
    viewmodel: WorkoutViewModel = koinViewModel(),
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
