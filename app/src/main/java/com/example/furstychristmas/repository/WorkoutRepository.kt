package com.example.furstychristmas.repository

import android.content.res.AssetManager
import com.example.furstychristmas.model.Drill
import com.example.furstychristmas.model.Workout
import com.example.furstychristmas.util.Constants
import com.example.furstychristmas.util.Util
import java.util.*

class WorkoutRepository(private val assetManager: AssetManager) {
    private val workouts = Util.getDrillPresets(assetManager, if (Constants.IS_CHEER_APP) "cheer.json" else "excersises.json")

    fun getWorkoutOfDay(day: Int): Workout {

        val date = Calendar.Builder().apply {
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
            set(Calendar.DAY_OF_MONTH, day)
        }.build()
        return if (Constants.IS_CHEER_APP)
            createCheerWorkout(date, day)
        else
            createFootballWorkout(date, day)
    }

    private fun createCheerWorkout(date: Calendar, day: Int): Workout {
        var motto = "tabata_1"
        val drills = mutableListOf<Drill>()
        var time = 30
        var sets = 2
        // 2 1 2 1 2 1 2 1 2 1 2 1 2 1
        when (date.get(Calendar.DAY_OF_MONTH)) {
            1, 8, 16, 23 -> motto = "tabata 1"
            2, 10, 17 -> motto = "tabata 2"
            4, 11, 19 -> motto = "tabata 3"
            5, 13, 20 -> motto = "tabata 4"
            7, 14, 22 -> motto = "jump training"
            else -> motto = "frei"
        }
        if (motto == "jump training") {
            sets = 1
        }
        drills.addAll(workouts.getOrDefault(motto, emptyList()))
        return Workout(day, drills, sets, motto.toUpperCase(), time)
    }

    private fun createFootballWorkout(date: Calendar, day: Int): Workout {
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