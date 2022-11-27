package redtoss.example.furstychristmas.eula


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import redtoss.example.furstychristmas.PREFERENCES_EULA_ACCEPTED
import redtoss.example.furstychristmas.ui.theme.DayCompleted
import redtoss.example.furstychristmas.ui.theme.DayNotCompleted
import redtoss.example.furstychristmas.ui.theme.FurstyChrismasTheme
import timber.log.Timber
import java.io.InputStream


class EulaActivity : ComponentActivity() {
    private val preferences: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")
        setContent {
            FurstyChrismasTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val coroutineScope = rememberCoroutineScope()
                    val snackbarHostState = remember { SnackbarHostState() }
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Eula(modifier = Modifier.padding(8.dp))
                        SnackbarHost(hostState = snackbarHostState, Modifier.padding(5.dp))
                        Buttons(coroutineScope, snackbarHostState)
                    }
                }
            }
        }
    }

    @Composable
    private fun Buttons(coroutineScope: CoroutineScope, snackbarHostState: SnackbarHostState) {
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
                    onClick = { confirmEULA(preferences = preferences) },
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
                    onClick = { finish() },
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

    @Composable
    private fun Eula(modifier: Modifier = Modifier.padding(0.dp)) {
        val eula = intent.extras!!.getInt("eula")
        val inputStream: InputStream = resources.openRawResource(eula)
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

    // NOTE: Here you would call your api to save the status
    private fun confirmEULA(preferences: SharedPreferences) {
        preferences.edit().putBoolean(PREFERENCES_EULA_ACCEPTED, true).apply()
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

}
