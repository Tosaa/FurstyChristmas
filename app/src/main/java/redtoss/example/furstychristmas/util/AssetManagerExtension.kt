package redtoss.example.furstychristmas.util

import android.content.res.AssetManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader

internal suspend fun AssetManager.readJson(filename: String): String {
    Timber.d("readJson(): $filename")
    return withContext(Dispatchers.IO) {
        return@withContext BufferedReader(
            InputStreamReader(
                this@readJson.open(filename),
                "UTF-8"
            )
        ).readText()
    }
}
