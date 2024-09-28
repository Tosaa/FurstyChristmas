package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.viewmodel.ExerciseViewModel

@Composable
fun ExercisePreview(
    viewmodel: ExerciseViewModel = koinViewModel(),
    selectedExercise: String,
    onClose: () -> Unit
) {
    viewmodel.selectedExercise = selectedExercise
    val title = viewmodel.title.collectAsState(initial = "")
    val startPosition = viewmodel.startPosition.collectAsState(initial = "")
    val executionDescription = viewmodel.description.collectAsState(initial = "")
    ExerciseUI(title.value, startPosition.value, executionDescription.value)
}

@Composable
private fun ExerciseUI(title: String, startPosition: String, executionDescription: String) {
    Column(Modifier.scrollable(rememberScrollState(), Orientation.Vertical).padding(horizontal = 10.dp)) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 26.sp
        )
        Row { }
        Divider()
        Spacer(Modifier.height(10.dp))
        Card(
            Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()) {
            Column(Modifier.padding( 5.dp)) {
                Text("Ausgangsposition:", fontSize = 20.sp, color = DayCompleted, modifier = Modifier.padding(bottom = 5.dp))
                Text(text = startPosition, fontSize = 20.sp)
            }
        }
        Spacer(Modifier.height(10.dp))
        Card(
            Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()) {
            Column(Modifier.padding( 5.dp)) {
                Text("Ausführung:", fontSize = 20.sp, color = DayCompleted, modifier = Modifier.padding(bottom = 5.dp))
                Text(text = executionDescription, fontSize = 20.sp)
            }
        }
    }
}
