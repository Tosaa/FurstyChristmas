package redtoss.example.furstychristmas.screen.christmas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import redtoss.example.furstychristmas.databinding.FragmentLastDayBinding
import redtoss.example.furstychristmas.domain.day.usecase.DayCompletionStatusUseCase
import redtoss.example.furstychristmas.domain.workout.model.Exercise
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import redtoss.example.furstychristmas.model.Execution
import redtoss.example.furstychristmas.util.DateUtil.today
import java.time.LocalDate
import java.time.Month

class LastDay : Fragment() {
    val dayCompletionStatusUseCase: DayCompletionStatusUseCase by inject()
    val loadWorkoutUseCase: LoadWorkoutUseCase by inject()
    var adapter: HistoryAdapter = HistoryAdapter(emptyMap())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLastDayBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        observeCompletedDays()
        binding.workoutHistory.adapter = adapter
        CoroutineScope(Dispatchers.Main + Job()).launch {
            dayCompletionStatusUseCase.markDayAsDone(LocalDate.of(today().year, Month.DECEMBER, 24))
            val completedDays = IntRange(1, 24).map { LocalDate.of(today().year, Month.DECEMBER, it) }.filter { dayCompletionStatusUseCase.isDayDone(it) }
            updateAdapter(completedDays)
        }
        return binding.root
    }

    suspend private fun updateAdapter(completedDays: List<LocalDate>) {
        completedDays.map { loadWorkoutUseCase.getWorkoutAtDay(it) }
            .let { workouts ->
                val mutableExerciseMap = mutableMapOf<Exercise, MutableList<Execution>>()
                workouts.forEach { workout ->
                    workout?.drills?.forEach { drill ->
                        mutableExerciseMap.putIfAbsent(drill.exercise, mutableListOf())
                        repeat(workout.rounds) { mutableExerciseMap.get(drill.exercise)?.add(drill.repetition) }
                    }
                }
                mutableExerciseMap
                    .mapKeys { it.key.exerciseName }
                    .filterKeys { !it.equals("Pause", true) }
                    .mapValues { it.value.sumBy { it.amount }.toString() + it.value[0].unit() }
                    .let { adapter.updateItems(it) }
            }
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