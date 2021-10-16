package com.example.furstychristmas.screen.christmas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.furstychristmas.databinding.FragmentLastDayBinding
import com.example.furstychristmas.day.domain.usecase.DayCompletionStatusUseCase
import com.example.furstychristmas.util.DateUtil
import com.example.furstychristmas.workout.domain.repository.WorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.time.LocalDate
import java.time.Month

class LastDay : Fragment() {
    val dayCompletionStatusUseCase: DayCompletionStatusUseCase by inject()
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
            dayCompletionStatusUseCase.markDayAsDone(LocalDate.of(DateUtil.today().year, Month.DECEMBER, 24))
        }
        binding.workoutHistory.adapter = adapter
        return binding.root
    }

    private fun observeCompletedDays() {
        /*
        dayCompletionRepository.cards.observe(viewLifecycleOwner) {

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
        }*/
    }

}