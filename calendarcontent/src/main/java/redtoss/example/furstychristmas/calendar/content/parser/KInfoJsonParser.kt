package redtoss.example.furstychristmas.calendar.content.parser

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import redtoss.example.furstychristmas.calendar.content.AppContent
import redtoss.example.furstychristmas.calendar.content.info.InfoPageContent

class KInfoJsonParser : JsonParserInterface<AppContent.Info> {

    @Serializable
    data class InfoWrapper(
        val type: String,
        val info: AppContent.Info
    )

    override suspend fun parseList(content: String): List<AppContent.Info> {

        return try {
            Json.decodeFromString<List<InfoWrapper>>(content)
                .map { it.info }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun parse(content: String): AppContent.Info? {
        // t.b.d.
        return null
    }
}