package com.example.furstychristmas.repository.content

import com.example.furstychristmas.model.content.DayContent
import com.example.furstychristmas.model.content.InfoPageContent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoRepository {
    fun getContent(): List<DayContent> = listOf(
        DayContent.Info(
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