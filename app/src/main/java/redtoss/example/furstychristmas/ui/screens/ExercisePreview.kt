package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.get
import redtoss.example.furstychristmas.ui.viewmodel.ExerciseViewModel

@Composable
fun ExercisePreview(
    viewmodel: ExerciseViewModel = get(),
    onClose: () -> Unit
) {
    Column() {
        Text("Exercise Preview")
        Text("For Exercise: ${viewmodel.selectedExercise}")
    }
}