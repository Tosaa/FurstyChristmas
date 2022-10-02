package redtoss.example.furstychristmas.domain.info.util

import android.content.res.AssetManager
import android.content.res.Resources
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.model.InfoPageContent
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoJsonParser(private val assetManager: AssetManager, private val resources: Resources) {
    private data class Page(val subtitle: String, val imageid: String, val htmltext: String)
    private data class Info(
        val date: String,
        val title: String,
        val pages: List<Page>
    )

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private data class Content(
        val type: String,
        val info: Info
    )

    val type = Types.newParameterizedType(List::class.java, Content::class.java)
    private val adapter = moshi.adapter<List<Content>>(type)

    private var loadInfoContent = emptyList<InfoContent>()

    suspend fun loadInfoOf(year: String): List<InfoContent> {
        if (loadInfoContent.isEmpty()) {
            loadInfoContent = withContext(Dispatchers.IO) {
                val contentPlain = loadContent(year, assetManager)
                Timber.i("load plain infos: ${contentPlain.size}")
                return@withContext contentPlain.mapNotNull { plainInfo ->
                    try {
                        InfoContent(
                            date = LocalDate.parse(
                                plainInfo.date,
                                DateTimeFormatter.ISO_LOCAL_DATE
                            ),
                            title = plainInfo.title,
                            pages = plainInfo.pages.map {
                                InfoPageContent(
                                    it.subtitle,
                                    it.imageid,
                                    it.htmltext
                                )
                            }
                        )
                    } catch (exception: Exception) {
                        Timber.e(exception.fillInStackTrace())
                        null
                    }
                }
            }
        }
        return loadInfoContent
    }

    private suspend fun loadContent(year: String, assetManager: AssetManager): List<Info> {
        val path = "calendar${year}_info.json"
        return withContext(Dispatchers.IO) {
            try {
                val json = BufferedReader(
                    InputStreamReader(
                        assetManager.open(path),
                        "UTF-8"
                    )
                ).readText()
                val infos = adapter.fromJson(json)
                return@withContext infos?.map { it.info }
            } catch (exception: Exception) {
                Timber.e(exception.fillInStackTrace())
                return@withContext emptyList<Info>()
            }
        } ?: emptyList()
    }
}
