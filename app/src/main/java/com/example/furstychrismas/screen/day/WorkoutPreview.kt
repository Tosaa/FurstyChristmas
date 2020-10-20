package com.example.furstychrismas.screen.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furstychrismas.databinding.WorkoutPreviewFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WorkoutPreview : Fragment() {

    val args: WorkoutPreviewArgs by navArgs()
    private val viewModel: WorkoutViewModel by viewModel<WorkoutViewModel> { parametersOf(args.day) }
    private lateinit var binding: WorkoutPreviewFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WorkoutPreviewFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewmodel = viewModel

        binding.exercises.layoutManager = LinearLayoutManager(requireContext())
        binding.muscleGroups.layoutManager = GridLayoutManager(requireContext(), 2)

        binding.workoutDoneButton.setOnClickListener {
            viewModel.markAsDone()
        }

        viewModel.exercises.observe(viewLifecycleOwner) { drills ->
            binding.exercises.adapter = WorkoutAdapter(drills)
        }

        viewModel.muscleGroups.observe(viewLifecycleOwner) {
            binding.muscleGroups.adapter =
                MuscleIconAdapter(it.distinct())
        }
        return binding.root
    }

}