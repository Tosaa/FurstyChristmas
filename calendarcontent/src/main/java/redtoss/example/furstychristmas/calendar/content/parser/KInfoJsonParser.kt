package redtoss.example.furstychristmas.calendar.content.parser

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import redtoss.example.furstychristmas.calendar.content.info.InfoContent
import redtoss.example.furstychristmas.calendar.content.info.InfoPageContent

class KInfoJsonParser : JsonParserInterface {
    @Serializable
    data class Page(val subtitle: String, val imageid: String, val htmltext: String)

    @Serializable
    data class Info(
        val date: String,
        val title: String,
        val pages: List<Page>
    )

    @Serializable
    data class Content(
        val type: String,
        val info: Info
    )

    override suspend fun parseList(content: String): List<InfoContent> {

        return try {
            Json.decodeFromString<List<Content>>(content)
                .map { it.info }
                .map { plainInfo ->
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
                }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<InfoContent>()
        }
    }

    override suspend fun parse(content: String): InfoContent? {
        // t.b.d.
        return null
    }
}