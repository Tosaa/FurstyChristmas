package redtoss.example.furstychristmas.domain.info.repository

import android.content.res.AssetManager
import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.util.InfoJsonParser
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.readJson
import timber.log.Timber

class InfoRepository(private val infoParser: InfoJsonParser, private val assetManager: AssetManager) {
    private var infos = mutableListOf<InfoContent>()

    suspend fun getContent(): List<InfoContent> {
        if (infos.isEmpty()) {
            (2021..(DateUtil.today().year + 1)).forEach { year ->
                val filename = "calendar${year}_info.json"
                val foundJson = try {
                    assetManager.readJson(filename)
                } catch (e: Exception) {
                    Timber.d("getContent(): cannot find $filename")
                    return@forEach
                }
                infos.addAll(infoParser.parseList(foundJson))
            }
        }
        return infos
    }
}
