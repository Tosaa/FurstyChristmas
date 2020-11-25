package com.example.furstychristmas.screen.christmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import com.example.furstychristmas.databinding.FragmentLastDayBinding
import com.example.furstychristmas.model.Execution
import com.example.furstychristmas.repository.CardRepository
import com.example.furstychristmas.repository.WorkoutRepository
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject

class LastDay : Fragment() {
    val cardRepository: CardRepository by inject()
    val repository: WorkoutRepository by inject()
    var adapter: HistoryAdapter = HistoryAdapter(emptyMap())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLastDayBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        observeCompletedDays()
        CoroutineScope(Dispatchers.Main + Job()).launch {
            cardRepository.markDayAsDone(24)
        }
        binding.workoutHistory.adapter = adapter
        return binding.root
    }

    private fun observeCompletedDays() {
        cardRepository.cards.observe(viewLifecycleOwner) {
            val completedDrills = mutableMapOf<String, Execution>()

            val days = it.filter { it.isDone && it.day.dayOfMonth < 24 }.map { it.day.dayOfMonth }
            val map = days.flatMap {
                val workout = repository.getWorkoutOfDay(it)
                workout.getExecutionPerDrill().toList()
            }

            map.forEach { (drill, execution) ->
                val exerciseName = drill.exercise.exerciseName

                if (completedDrills.containsKey(exerciseName)) {
                    completedDrills.put(
                        exerciseName,
                        completedDrills[exerciseName]?.withOther(execution) ?: execution
                    )
                } else {
                    completedDrills.putIfAbsent(exerciseName, execution)
                }
            }

            adapter.updateItems(completedDrills.toSortedMap().mapValues { "${it.value.amount} ${it.value.unit()}" }.filter { it.key != "Pause" })
        }
    }

}