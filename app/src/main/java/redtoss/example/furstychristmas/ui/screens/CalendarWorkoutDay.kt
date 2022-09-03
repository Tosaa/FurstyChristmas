@file:OptIn(ExperimentalMaterialApi::class)

package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.get
import redtoss.example.furstychristmas.domain.workout.model.Drill
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
    Column {
        CalendarHeader(date = viewmodel.date, isDayCompleted.value)
        Card(modifier = Modifier.padding(32.dp)) {
            Column {
                WorkoutTitle(title = motto)
                Exercises(exercises, onExerciseClicked)
                Rounds(rounds)
                DoneButton(isDayCompleted) {
                    viewmodel.completeDay()
                    onDayCompletedClicked(viewmodel.date)
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
    exercises.value.forEach {
        Card(
            onClick = { onExerciseClicked(it.exercise.exerciseId) },
            shape = CutCornerShape(5.dp),
            border = BorderStroke(2.dp, Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = it.exercise.exerciseName,
                    modifier = Modifier
                        .weight(3f)
                        .padding(6.dp),
                    fontSize = 20.sp,
                    softWrap = false
                )
                Text(
                    text = it.repetition.formattedString().toString(),
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Red)
                        .padding(6.dp),
                    fontSize = 20.sp,
                )
            }
        }
    }
}

@Composable
private fun Rounds(rounds: State<Int>) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${rounds.value}-Runden")
    }
}


@Composable
private fun DoneButton(isDayCompleted: State<Boolean>, onDoneClicked: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isDayCompleted.value) {
            Button(onClick = {
                onDoneClicked
            }) {
                Text(text = "Done")
            }
        }
    }
}