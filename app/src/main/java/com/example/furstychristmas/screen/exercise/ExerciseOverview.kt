package com.example.furstychristmas.screen.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furstychristmas.databinding.ExerciseListFragmentBinding
import com.example.furstychristmas.domain.workout.usecase.LoadExerciseUseCase
import org.koin.android.ext.android.inject

class ExerciseOverview : Fragment() {

    private lateinit var binding: ExerciseListFragmentBinding
    private val exerciseUseCase: LoadExerciseUseCase by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExerciseListFragmentBinding.inflate(inflater, container, false)
        lifecycleScope.launchWhenResumed {
            binding.exerciseList.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ExerciseAdapter(
                    exerciseUseCase.loadAllExercises().filter { it.exerciseId != "BREAK" },
                    findNavController()
                )
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
        return binding.root
    }
}