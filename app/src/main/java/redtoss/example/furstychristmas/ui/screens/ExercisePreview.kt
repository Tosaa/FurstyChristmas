package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
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
    Card(modifier = Modifier.padding(16.dp)) {
        Column(Modifier.scrollable(rememberScrollState(), Orientation.Vertical)) {
            ContentTitle(title = title)
            Text("Ausgangsposition:", fontSize = 12.sp)
            Text(text = startPosition.value, modifier = Modifier.padding(start = 8.dp), fontSize = 20.sp)
            Divider()
            Text("Ausführung:", fontSize = 12.sp)
            Text(text = executionDescription.value, modifier = Modifier.padding(start = 8.dp), fontSize = 20.sp)
        }
    }
}

@Composable
private fun ContentTitle(title: State<String>) {
    Text(
        text = title.value,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,
        fontSize = 26.sp
    )
}
