package redtoss.example.furstychristmas.calendar.content.info

import java.time.LocalDate
import redtoss.example.furstychristmas.calendar.content.AppContent

data class InfoContent(val date: LocalDate, val title: String, val pages: List<InfoPageContent>) : AppContent
