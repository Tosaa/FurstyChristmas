package redtoss.example.furstychristmas.domain.info.usecase

import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.repository.InfoRepository
import redtoss.example.furstychristmas.util.DateUtil.sameDayAs
import timber.log.Timber
import java.time.LocalDate

class LoadInfoUseCase(private val infoRepository: InfoRepository) {
    suspend fun getInfoAtDay(localDate: LocalDate): InfoContent? {
        val infos = infoRepository.getContent()
        val info = infos.firstOrNull { it.date.sameDayAs(localDate) }
        if (info == null) {
            Timber.w(
                "getInfoAtDay(): cannot find Info for $localDate in ${
                    infos.joinToString { it.date.toString() }
                }"
            )
        }
        Timber.i("getInfoAtDate: $localDate = $info")
        return info
    }
}
