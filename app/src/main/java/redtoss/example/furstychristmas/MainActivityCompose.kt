package redtoss.example.furstychristmas

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import org.koin.android.ext.android.inject
import redtoss.example.furstychristmas.eula.EulaActivity
import redtoss.example.furstychristmas.ui.MyAppNavHost
import redtoss.example.furstychristmas.ui.theme.FurstyChrismasTheme
import timber.log.Timber

class MainActivityCompose : ComponentActivity() {
    private val preferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentAppVersion = BuildConfig.VERSION_CODE
        val storedVersionCode = getStoredVersion()
        Timber.d("currentAppVersion: $currentAppVersion, storedAppVersion: $storedVersionCode")
        if (storedVersionCode != currentAppVersion) {
            resetAppWithVersion(currentAppVersion)
        }
        checkEula()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        setContent {
            FurstyChrismasTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MyAppNavHost()
                }
            }
        }
    }

    private fun getStoredVersion(): Int {
        return preferences.getInt(PREFERENCES_APP_VERSION, -1)
    }

    private fun resetAppWithVersion(version: Int) {
        Timber.d("resetAppWithVersion(): $version")
        preferences.edit()
            .putInt(PREFERENCES_APP_VERSION, version)
            .putBoolean(PREFERENCES_EULA_ACCEPTED, false)
            .commit()
    }

    private fun checkEula() {
        val isEulaAccepted = preferences.getBoolean(PREFERENCES_EULA_ACCEPTED, false)
        if (!isEulaAccepted) {
            Timber.d("checkEula(): Eula has not been Accepted yet -> show Eula")

            val eulaResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    Timber.d("onEulaResult: $it")
                }
            }
            val intent = Intent(this, EulaActivity::class.java)
            val bundle = Bundle()
            val eula = R.raw.eula2021
            bundle.putInt("eula", eula)
            intent.putExtras(bundle)
            eulaResultLauncher.launch(intent)
        } else {
            Timber.d("checkEula(): Eula has been Accepted")
        }
    }
}
