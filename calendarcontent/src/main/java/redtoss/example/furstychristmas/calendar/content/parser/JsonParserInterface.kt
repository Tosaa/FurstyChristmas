package redtoss.example.furstychristmas.calendar.content.parser

import redtoss.example.furstychristmas.calendar.content.AppContent

interface JsonParserInterface {

    suspend fun parseList(content: String): List<AppContent>
    suspend fun parse(content:String): AppContent?

}
