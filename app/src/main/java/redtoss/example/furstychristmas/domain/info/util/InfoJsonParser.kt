package redtoss.example.furstychristmas.domain.info.util

import android.content.res.AssetManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.model.InfoPageContent
import redtoss.example.furstychristmas.domain.jsonparser.JsonParserInterface
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoJsonParser(private val assetManager: AssetManager) : JsonParserInterface {
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

    private val type = Types.newParameterizedType(List::class.java, Content::class.java)
    private val adapter = moshi.adapter<List<Content>>(type)

    override suspend fun parseList(content: String): List<InfoContent> {
        Timber.d("parseList()")
        return withContext(Dispatchers.IO) {
            var list: List<Content>? = emptyList<Content>()
            try {
                list = adapter.fromJson(content)
            } catch (e: Exception) {
                Timber.e(e, "cannot read json")
                Timber.d("bad json: $content")
            }
            list
                ?.map { it.info }
                ?.map { plainInfo ->
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
                        })
                } ?: emptyList()
        }
    }

    override suspend fun parse(content: String): InfoContent? {
        // t.b.d.
        return null
    }
}
