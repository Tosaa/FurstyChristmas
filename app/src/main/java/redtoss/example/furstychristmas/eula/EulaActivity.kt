package redtoss.example.furstychristmas.eula


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import redtoss.example.furstychristmas.ui.theme.FurstyChrismasTheme
import timber.log.Timber
import java.io.InputStream


class EulaActivity : ComponentActivity() {
    val injectedPreferences: SharedPreferences = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate()")
        setContent {
            FurstyChrismasTheme {
                val coroutineScope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Eula()
                    SnackbarHost(hostState = snackbarHostState, Modifier.padding(5.dp))
                    if (!injectedPreferences.getBoolean("eulaAccepted", false)) {
                        Row(Modifier.fillMaxWidth()) {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(message = "Du musst die End Nutzer Vereinbarung akzeptieren um die App nutzen zu können.", actionLabel = null, duration = SnackbarDuration.Long)
                                    }
                                },
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .weight(1f)
                            ) {
                                Text("Cancel")
                            }
                            Button(
                                onClick = { confirmEULA(preferences = injectedPreferences) },
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .weight(1f)
                            ) {
                                Text("Confirm")
                            }
                        }
                    }
                }
            }

        }
    }

    @Composable
    private fun Eula(modifier: Modifier = Modifier.padding(0.dp)) {
        val eula = intent.extras!!.getInt("eula")
        val inputStream: InputStream = resources.openRawResource(eula)
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes)
        AndroidView(
            modifier = modifier,
            factory = { context -> TextView(context) },
            update = { it.text = HtmlCompat.fromHtml(String(bytes), HtmlCompat.FROM_HTML_MODE_COMPACT) }
        )
    }

    // NOTE: Here you would call your api to save the status
    private fun confirmEULA(preferences: SharedPreferences) {
        preferences.edit().putBoolean("eulaAccepted", true).apply()
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

}
