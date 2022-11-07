package redtoss.example.furstychristmas.domain.info.repository

import android.content.res.AssetManager
import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.model.InfoPageContent
import redtoss.example.furstychristmas.domain.info.util.InfoJsonParser
import redtoss.example.furstychristmas.util.readJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoRepository(private val infoParser: InfoJsonParser, private val assetManager: AssetManager) {
    private var infos = mutableListOf<InfoContent>()

    suspend fun getContent(): List<InfoContent> {
        if (infos.isEmpty()) {
            listOf("2021", "2022").forEach { year ->
                infos.addAll(infoParser.parseList(assetManager.readJson("calendar${year}_info.json")))
            }
        }
        return infos
    }

    private val dummyContent = listOf(
        InfoContent(
            date = LocalDate.parse("2021-12-01", DateTimeFormatter.ISO_LOCAL_DATE),
            title = "test-title",
            pages = listOf(
                InfoPageContent(
                    title = "",
                    imageid = null,
                    text = "test-text"
                )
            )
        )
    )
}
