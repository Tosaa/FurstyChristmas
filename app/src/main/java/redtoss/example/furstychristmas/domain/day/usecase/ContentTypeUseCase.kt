package redtoss.example.furstychristmas.domain.day.usecase

import redtoss.example.furstychristmas.domain.info.repository.InfoRepository
import redtoss.example.furstychristmas.domain.workout.repository.WorkoutRepository
import redtoss.example.furstychristmas.util.DateUtil.sameDayAs
import java.time.LocalDate

class ContentTypeUseCase(private val workoutRepository: WorkoutRepository, private val infoRepository: InfoRepository) {

    suspend fun getTypeForDate(date: LocalDate): Type? {
        return when {
            workoutRepository.getContent().find { it.date.sameDayAs(date) } != null ->
                Type.WORKOUT

            infoRepository.getContent().find { it.date.sameDayAs(date) } != null ->
                Type.INFO

            else ->
                null
        }
    }

    enum class Type {
        WORKOUT, INFO
    }
}
