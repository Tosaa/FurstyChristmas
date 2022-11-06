package redtoss.example.furstychristmas.domain.info.repository

import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.model.InfoPageContent
import redtoss.example.furstychristmas.domain.info.util.InfoJsonParser
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoRepository(private val infoParser: InfoJsonParser) {
    suspend fun getContent(): List<InfoContent> = getContentOf2021().plus(getContentOf2022())

    private suspend fun getContentOf2021(): List<InfoContent> = infoParser.loadInfoOf("2021")
    private suspend fun getContentOf2022(): List<InfoContent> = infoParser.loadInfoOf("2022")


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
