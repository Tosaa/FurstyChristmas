package redtoss.example.furstychristmas.domain.day.usecase

import java.time.LocalDate
import redtoss.example.furstychristmas.domain.info.repository.InfoRepository
import redtoss.example.furstychristmas.domain.workout.repository.WorkoutRepository
import redtoss.example.furstychristmas.util.DateUtil.sameDayAs
import timber.log.Timber

class ContentTypeUseCase(private val workoutRepository: WorkoutRepository, private val infoRepository: InfoRepository) {

    suspend fun getTypeForDate(date: LocalDate): Type {
        Timber.d("getTypeForDate(): date = $date")
        return when {
            workoutRepository.getContent().find { it.date.sameDayAs(date) } != null ->
                Type.WORKOUT

            infoRepository.getContent().find { it.date.sameDayAs(date) } != null ->
                Type.INFO

            else ->
                Type.NONE
        }
    }

    enum class Type {
        WORKOUT, INFO, NONE
    }
}
