package redtoss.example.furstychristmas.domain.workout.usecase

import redtoss.example.furstychristmas.domain.workout.model.WorkoutContent
import redtoss.example.furstychristmas.domain.workout.repository.WorkoutRepository
import redtoss.example.furstychristmas.util.DateUtil.sameDayAs
import java.time.LocalDate

class LoadWorkoutUseCase(private val workoutRepository: WorkoutRepository) {

    suspend fun getWorkoutAtDay(localDate: LocalDate): WorkoutContent? {
        return workoutRepository.getContent().find { it.date.sameDayAs(localDate) }
    }
}
