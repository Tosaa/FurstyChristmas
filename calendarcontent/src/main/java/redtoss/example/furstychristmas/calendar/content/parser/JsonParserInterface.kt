package redtoss.example.furstychristmas.calendar.content.parser

import redtoss.example.furstychristmas.calendar.content.AppContent

interface JsonParserInterface<T:AppContent> {

    suspend fun parseList(content: String): List<T>
    suspend fun parse(content:String): T?

}
