package redtoss.example.furstychristmas.domain.workout.usecase

import java.time.LocalDate
import redtoss.example.furstychristmas.calendar.content.workout.WorkoutContent
import redtoss.example.furstychristmas.domain.workout.repository.WorkoutRepository
import redtoss.example.furstychristmas.util.DateUtil.sameDayAs

class LoadWorkoutUseCase(private val workoutRepository: WorkoutRepository) {

    suspend fun getWorkoutAtDay(localDate: LocalDate): WorkoutContent? {
        return workoutRepository.getContent().find { it.date.sameDayAs(localDate) }
    }
}
