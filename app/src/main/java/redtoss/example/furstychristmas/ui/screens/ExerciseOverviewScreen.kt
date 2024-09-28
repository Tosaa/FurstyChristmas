package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.viewmodel.ExerciseOverviewViewModel

@Composable
fun ExerciseOverviewScreen(
    onExerciseClicked: (Exercise) -> Unit,
    overviewViewModel: ExerciseOverviewViewModel = koinViewModel()
) {
    val exercises = overviewViewModel.exercises.collectAsState(initial = emptyList())
    LazyColumn() {
        items(exercises.value) { exercise ->
            Button(
                onClick = { onExerciseClicked(exercise) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DayCompleted
                )
            ) {
                Text(exercise.exerciseName)
            }
        }
    }
}
