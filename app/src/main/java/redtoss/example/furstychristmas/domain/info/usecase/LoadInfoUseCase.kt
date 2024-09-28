package redtoss.example.furstychristmas.domain.info.usecase

import java.time.LocalDate
import redtoss.example.furstychristmas.domain.info.model.InfoContent
import redtoss.example.furstychristmas.domain.info.repository.InfoRepository
import redtoss.example.furstychristmas.util.DateUtil.sameDayAs

class LoadInfoUseCase(private val infoRepository: InfoRepository) {
    suspend fun getInfoAtDay(localDate: LocalDate): InfoContent? {
        return infoRepository.getContent().find { it.date.sameDayAs(localDate) }
    }
}
