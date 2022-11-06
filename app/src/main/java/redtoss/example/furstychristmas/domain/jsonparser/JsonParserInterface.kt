package redtoss.example.furstychristmas.domain.jsonparser

import redtoss.example.furstychristmas.domain.info.util.AppContent

interface JsonParserInterface {

    suspend fun getContent(): List<AppContent>

}
