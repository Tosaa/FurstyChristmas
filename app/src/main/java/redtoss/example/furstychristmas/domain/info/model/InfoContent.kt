package redtoss.example.furstychristmas.domain.info.model

import redtoss.example.furstychristmas.domain.info.util.AppContent
import java.time.LocalDate

/**
 * Todo add Documentation
 */
data class InfoContent(val date: LocalDate, val title: String, val pages: List<InfoPageContent>) : AppContent
