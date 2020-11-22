package com.example.furstychrismas.repository

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.furstychrismas.model.Drill
import com.example.furstychrismas.model.Workout
import com.example.furstychrismas.util.Util
import java.util.*

class WorkoutRepository(private val assetManager: AssetManager) {

    private val workouts = Util.getDrillPresets(assetManager)

    fun getWorkoutOfDay(day: Int): LiveData<Workout> {
        Log.i("WorkoutRepository", "load day $day")

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
        Log.i("WorkoutRepository", "todays workout:$workout")
        return liveData { emit(workout) }
    }


}