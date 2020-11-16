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

        var sets = 5
        val drills = mutableListOf<Drill>()

        // FLEX
        // CHEST
        // CORE
        // FLEX | ALL
        // LEGS
        // FLEX
        // CORE
        var motto = "dehnen"
        when (date.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> {
                motto = "bauch"
            }
            Calendar.TUESDAY -> {
                if (date.get(Calendar.WEEK_OF_MONTH) % 2 == 0) {
                    motto = "brust var1"
                } else {
                    motto = "brust var2"
                }
            }
            Calendar.WEDNESDAY -> {
                motto = "alles"
            }
            Calendar.THURSDAY -> {
                motto = "beine"
            }

            Calendar.FRIDAY -> {
                motto = "dehnen"
            }
            Calendar.SATURDAY -> {
                motto = "bauch"
            }
            Calendar.SUNDAY -> {
                motto = "alles"
            }
        }
        drills.addAll(workouts.getOrDefault(motto, emptyList()))
        val workout = Workout(day, drills, sets,motto.split(" ").first().toUpperCase())
        Log.i("WorkoutRepository", "todays workout:$workout")
        return liveData { emit(workout) }
    }


}