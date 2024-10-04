package redtoss.example.furstychristmas.domain.info.repository

import android.content.res.AssetManager
import redtoss.example.furstychristmas.calendar.content.AppContent
import redtoss.example.furstychristmas.calendar.content.parser.KInfoJsonParser
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.readJson
import timber.log.Timber

class InfoRepository(private val infoParser: KInfoJsonParser, private val assetManager: AssetManager) {
    private var infos = mutableListOf<AppContent.Info>()

    suspend fun getContent(): List<AppContent.Info> {
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
