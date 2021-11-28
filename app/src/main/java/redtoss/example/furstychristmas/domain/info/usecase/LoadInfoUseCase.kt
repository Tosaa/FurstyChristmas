package redtoss.example.furstychristmas.domain.info.usecase

import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.repository.InfoRepository
import timber.log.Timber
import java.time.LocalDate

class LoadInfoUseCase(private val infoRepository: InfoRepository) {
    suspend fun getInfoAtDay(localDate: LocalDate): InfoContent? {
        val info = infoRepository.getContent().firstOrNull { it.date == localDate }
        Timber.i("getInfoAtDate: $localDate = $info")
        return info
    }
}