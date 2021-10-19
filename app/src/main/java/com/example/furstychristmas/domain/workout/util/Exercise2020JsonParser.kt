package com.example.furstychristmas.domain.workout.util

import android.content.res.AssetManager
import com.example.furstychristmas.domain.workout.model.Drill
import com.example.furstychristmas.domain.workout.model.WorkoutContent
import com.example.furstychristmas.model.Workout
import com.example.furstychristmas.util.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Month
import java.util.*

class Exercise2020JsonParser(private val assetManager: AssetManager) {
    private val workouts = Util.getDrillPresets(assetManager)

    suspend fun getContentOf(year: String): List<WorkoutContent> = withContext(Dispatchers.IO) {
        if (year == "2020") {
            return@withContext IntRange(1, 24).map { getWorkoutOfDay(it) }.map { workout ->
                WorkoutContent(
                    date = LocalDate.of(2020, Month.DECEMBER, workout.day),
                    drills = workout.drills,
                    rounds = workout.workoutRepetition,
                    bodyparts = workout.drills.flatMap { drill -> drill.exercise.muscles.map { it.muscle } },
                    motto = workout.motto,
                    durationInMinutes = workout.time
                )
            }
        } else {
            return@withContext emptyList<WorkoutContent>()
        }
    }

    fun getWorkoutOfDay(day: Int): Workout {

        val date = Calendar.Builder().apply {
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
            set(Calendar.DAY_OF_MONTH, day)
        }.build()

        var sets = if (date.get(Calendar.DAY_OF_MONTH) < 14) 5 else 6
        val drills = mutableListOf<Drill>()

        // MO
        // BRUST
        // DI
        // BAUCH
        // MI
        // ALLES
        // DO
        // DEHNEN
        // FR
        // ALLES
        // SA
        // BEINE
        // SO
        // DEHNEN
        var motto = "dehnen"
        var time = 30
        when (date.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> {
                if (date.get(Calendar.WEEK_OF_MONTH) % 2 == 0) {
                    motto = "brust var1"
                } else {
                    motto = "brust var2"
                }
            }
            Calendar.TUESDAY -> {
                motto = "bauch"
            }
            Calendar.WEDNESDAY -> {
                motto = "alles var1"
            }
            Calendar.THURSDAY -> {
                motto = "dehnen"
            }
            Calendar.FRIDAY -> {
                motto = "alles var2"
            }
            Calendar.SATURDAY -> {
                motto = "beine"
            }
            Calendar.SUNDAY -> {
                motto = "dehnen"
            }
        }

        if (motto == "dehnen") {
            sets = 1
            time = 10
        }
        drills.addAll(workouts.getOrDefault(motto, emptyList()))
        val workout = Workout(day, drills, sets, motto.split(" ").first().toUpperCase(), time)
        return workout
    }

}