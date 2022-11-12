package redtoss.example.furstychristmas.domain.workout.usecase

import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.domain.workout.repository.ExerciseRepository
import timber.log.Timber

class LoadExerciseUseCase(private val exerciseRepository: ExerciseRepository) {

    suspend fun loadExerciseById(exerciseId: String): Exercise? {
        Timber.v("load exercise: $exerciseId")
        val foundExercise = exerciseRepository.getExercise(exerciseId)
        Timber.v("found: $foundExercise")
        return foundExercise
    }

    suspend fun loadAllExercises(): List<Exercise> {
        Timber.v("load all exercises")
        return exerciseRepository.getContent()
    }
}
