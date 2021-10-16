package com.example.furstychristmas.domain.info.repository

import com.example.furstychristmas.domain.info.model.InfoContent
import com.example.furstychristmas.domain.info.model.InfoPageContent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoRepository {
    fun getContent(): List<InfoContent> = listOf(
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