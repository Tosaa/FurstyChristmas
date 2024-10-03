@file:OptIn(ExperimentalMaterialApi::class)

package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
    val workoutFlow = viewmodel.workout.collectAsState(null)
    val isDayCompleted = viewmodel.isDone.collectAsState(initial = false)
    val workout = workoutFlow.value
    if (workout == null) {
        Text("Loading workout")
    } else {
        val motto = workout.motto
        val exercises = workout.drills
        val rounds = workout.rounds
        CalendarWorkoutContent(day, isDayCompleted.value, motto, exercises, onExerciseClicked, rounds) { day ->
            viewmodel.completeDay()
            onDayCompletedClicked(day)
            onClose()
        }
    }

}

@Composable
private fun CalendarWorkoutContent(
    date: LocalDate,
    isDayCompleted: Boolean,
    motto: String,
    exercises: List<Drill>,
    onExerciseClicked: (String) -> Unit,
    rounds: Int,
    onDayCompletedClicked: (LocalDate) -> Unit
) {
    Surface {
        Card(modifier = Modifier.padding(8.dp)) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = motto,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 26.sp
                )
                Exercises(exercises, onExerciseClicked)
                Spacer(Modifier.height(10.dp))
                Text(text = "$rounds-Runden", fontSize = 20.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.weight(1f))
                DoneButton(isDayCompleted) {
                    onDayCompletedClicked(date)
                }
            }
        }
    }
}


@Composable
private fun Exercises(
    exercises: List<Drill>,
    onExerciseClicked: (String) -> Unit
) {
    LazyColumn() {
        itemsIndexed(exercises) { index, drill ->
            Column(Modifier.padding(vertical = 5.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .fillMaxWidth()
                        .clickable {
                            onExerciseClicked(drill.exercise.exerciseId)
                        }
                ) {
                    Text("${index + 1}.", fontSize = 20.sp)
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
                    Column(Modifier.width(IntrinsicSize.Max)) {
                        Text(
                            text = drill.repetition.formattedAmount(),
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = drill.repetition.unit(),
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }
            Divider()
        }
    }
}


@Composable
private fun DoneButton(isDayCompleted: Boolean, onDoneClicked: () -> Unit) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isDayCompleted) {
            Button(
                onClick = onDoneClicked,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DayCompleted,
                    contentColor = MaterialTheme.colors.onPrimary
                )
            ) {
                Text(text = "Done", fontSize = 28.sp, modifier = Modifier.padding(10.dp))
            }
        }
    }
}
