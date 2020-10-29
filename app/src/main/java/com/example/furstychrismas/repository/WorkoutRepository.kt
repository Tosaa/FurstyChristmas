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


    fun getWorkoutOfDay(day: Int): LiveData<Workout> {

        val date = Calendar.Builder().apply {
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
            set(Calendar.DAY_OF_MONTH, day)
        }.build()

        var sets = 3 + date.get(Calendar.WEEK_OF_MONTH)
        val drills = mutableListOf<Drill>()

        val workouts = Util.getDrillPresets(assetManager)

        when (date.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> {
                if (date.get(Calendar.WEEK_OF_MONTH) % 2 == 0) {
                    drills.addAll(workouts.getOrDefault("chest var1", emptyList()))
                } else {
                    drills.addAll(workouts.getOrDefault("chest var2", emptyList()))
                }
            }
            Calendar.TUESDAY -> {
                drills.addAll(workouts.getOrDefault("legs", emptyList()))
            }
            Calendar.WEDNESDAY -> {
                drills.addAll(workouts.getOrDefault("all", emptyList()))
            }
            Calendar.THURSDAY -> {
                drills.addAll(workouts.getOrDefault("core", emptyList()))
            }
            Calendar.FRIDAY -> {
                drills.addAll(workouts.getOrDefault("flex", emptyList()))
            }
            Calendar.SATURDAY -> {
                drills.addAll(workouts.getOrDefault("core", emptyList()))
            }
            Calendar.SUNDAY -> {
                drills.addAll(workouts.getOrDefault("all", emptyList()))
            }
        }
        val workout = Workout(day, drills, sets)
        Log.i("DayRepository", "todays workout:$workout")
        return liveData { emit(workout) }
    }


}