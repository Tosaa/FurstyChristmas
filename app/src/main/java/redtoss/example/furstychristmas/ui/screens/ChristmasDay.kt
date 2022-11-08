package redtoss.example.furstychristmas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.get
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.model.Execution
import redtoss.example.furstychristmas.ui.viewmodel.ChristmasViewModel

@Composable
fun ChristmasDay(
    viewmodel: ChristmasViewModel = get(),
    year: Int,
    onClose: () -> Unit,
) {
    val completed = viewmodel.completedExercises.map { it.entries.toList() }.collectAsState(initial = emptyList<Map.Entry<Exercise, Execution>>())
    Column {
        Text(
            text = "Merry Christmas $year",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 26.sp
        )
        Text(
            text = "Das hast du alles in diesem Jahr geschafft!",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(items = completed.value) { entry ->
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
                ) {
                    Text(
                        text = entry.key.exerciseName,
                        modifier = Modifier
                            .padding(6.dp)
                            .width(200.dp),
                        fontSize = 20.sp,
                        maxLines = 2,
                        softWrap = true
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = entry.value.formattedString().toString(),
                        modifier = Modifier.padding(6.dp),
                        fontSize = 20.sp,
                        softWrap = false
                    )
                }
            }
        }
    }
}
