package redtoss.example.furstychristmas

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext
import redtoss.example.furstychristmas.ui.screens.RootScreen
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
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        setContent {
            KoinContext {
                FurstyChrismasTheme {
                    // A surface container using the 'background' color from the theme
                    RootScreen()
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
}
