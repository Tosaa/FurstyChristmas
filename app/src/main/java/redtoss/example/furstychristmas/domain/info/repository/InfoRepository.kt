package redtoss.example.furstychristmas.domain.info.repository

import android.content.res.AssetManager
import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.util.InfoJsonParser
import redtoss.example.furstychristmas.util.readJson

class InfoRepository(private val infoParser: InfoJsonParser, private val assetManager: AssetManager) {
    private var infos = mutableListOf<InfoContent>()

    suspend fun getContent(): List<InfoContent> {
        if (infos.isEmpty()) {
            listOf("2021", "2022").forEach { year ->
                infos.addAll(infoParser.parseList(assetManager.readJson("calendar${year}_info.json")))
            }
        }
        return infos
    }
}
