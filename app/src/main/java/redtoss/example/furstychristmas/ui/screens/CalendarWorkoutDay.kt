@file:OptIn(ExperimentalMaterialApi::class)

package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.get
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
    Card(modifier = Modifier.padding(16.dp)) {
        Column {
            Text(
                text = motto.value,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                textDecoration = TextDecoration.Underline
            )
            exercises.value.forEach {
                Card(
                    onClick = { onExerciseClicked(it.exercise.exerciseName) },
                    shape = CutCornerShape(5.dp),
                    border = BorderStroke(2.dp, Color.White)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = it.exercise.exerciseName,
                            modifier = Modifier
                                .weight(3f)
                                .padding(6.dp),
                            fontSize = 20.sp
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
            Row {
                Button(onClick = { onClose() }) {
                    Text(text = "Back")
                }
                Button(onClick = { onDayCompletedClicked(viewmodel.date) }) {
                    Text(text = "Done")
                }
            }
        }
    }
}