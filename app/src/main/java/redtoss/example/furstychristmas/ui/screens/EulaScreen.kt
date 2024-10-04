package redtoss.example.furstychristmas.ui.screens

import android.content.SharedPreferences
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.io.InputStream
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import redtoss.example.furstychristmas.PREFERENCES_EULA_ACCEPTED
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.theme.DayNotCompleted

@Composable
fun EulaScreen(eulaResource: Int, onClose: (eulaAgreed:Boolean) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Eula(modifier = Modifier.padding(8.dp), eulaResource)
        SnackbarHost(hostState = snackbarHostState, Modifier.padding(5.dp))
        EulaButtons(snackbarHostState, onClose)
    }
}

@Composable
private fun Eula(
    modifier: Modifier = Modifier.padding(0.dp),
    eula: Int
) {
    val context = LocalContext.current
    val inputStream: InputStream = remember { context.resources.openRawResource(eula) }
    val bytes = ByteArray(inputStream.available())
    inputStream.read(bytes)
    val backgroundColor = MaterialTheme.colors.background.toArgb()
    val textColor = MaterialTheme.colors.onBackground.toArgb()
    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).also {
                it.setBackgroundColor(backgroundColor)
                it.setTextColor(textColor)
            }
        },
        update = { it.text = HtmlCompat.fromHtml(String(bytes), HtmlCompat.FROM_HTML_MODE_LEGACY) }
    )
}

@Composable
private fun EulaButtons(snackbarHostState: SnackbarHostState, finish: (eulaAgreed:Boolean) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val preferences: SharedPreferences = koinInject()
    val isEulaAccepted = preferences.getBoolean(PREFERENCES_EULA_ACCEPTED, false)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (!isEulaAccepted) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message = "Du musst die End Nutzer Vereinbarung lesen und akzeptieren um die App nutzen zu können.", actionLabel = null, duration = SnackbarDuration.Long)
                    }
                },
                modifier = Modifier
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DayNotCompleted,
                    contentColor = MaterialTheme.colors.onPrimary,
                )

            ) {
                Text("Nein", maxLines = 1)
            }
            Button(
                onClick = {
                    confirmEULA(preferences = preferences)
                    finish(true)
                },
                modifier = Modifier
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DayCompleted,
                    contentColor = MaterialTheme.colors.onPrimary,
                )
            ) {
                Text("Verstanden", maxLines = 1)
            }
        } else {
            Spacer(Modifier)
            Button(
                onClick = { finish(true) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = DayCompleted,
                    contentColor = MaterialTheme.colors.onPrimary,
                )
            ) {
                Text("Zurück")
            }
            Spacer(Modifier)
        }
    }
}


// NOTE: Here you would call your api to save the status
private fun confirmEULA(preferences: SharedPreferences) {
    preferences.edit().putBoolean(PREFERENCES_EULA_ACCEPTED, true).apply()
}