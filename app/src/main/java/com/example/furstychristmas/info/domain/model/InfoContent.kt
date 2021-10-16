package com.example.furstychristmas.info.domain.model

import java.time.LocalDate

data class InfoContent(val date: LocalDate, val title: String, val pages: List<InfoPageContent>) {
}