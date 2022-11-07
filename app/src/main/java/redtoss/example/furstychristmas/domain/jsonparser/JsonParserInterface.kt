package redtoss.example.furstychristmas.domain.jsonparser

import redtoss.example.furstychristmas.domain.info.util.AppContent

interface JsonParserInterface {

    suspend fun parseList(content: String): List<AppContent>
    suspend fun parse(content:String): AppContent?

}
