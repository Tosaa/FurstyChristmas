package redtoss.example.furstychristmas.domain.info.model

import java.time.LocalDate
import redtoss.example.furstychristmas.domain.info.util.AppContent

data class InfoContent(val date: LocalDate, val title: String, val pages: List<InfoPageContent>) : AppContent
