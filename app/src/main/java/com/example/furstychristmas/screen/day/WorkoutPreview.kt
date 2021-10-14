package com.example.furstychristmas.screen.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.furstychristmas.databinding.WorkoutPreviewFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WorkoutPreview : Fragment() {

    val args: WorkoutPreviewArgs by navArgs()
    private val viewModel: WorkoutViewModel by viewModel<WorkoutViewModel> { parametersOf(LocalDate.parse(args.date, DateTimeFormatter.ISO_LOCAL_DATE)) }
    private lateinit var binding: WorkoutPreviewFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WorkoutPreviewFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewmodel = viewModel

        binding.warningDialog.cancelButton.setOnClickListener {
            binding.warningDialog.root.visibility = View.GONE
        }
        binding.workoutDoneButton.setOnClickListener {
            viewModel.markAsDone()
        }
        /*
        viewModel.exercises.observe(viewLifecycleOwner) { drills ->
            // binding.exercises.adapter = WorkoutAdapter(drills, findNavController())
        }

        viewModel.muscleGroups.observe(viewLifecycleOwner) {
            binding.workoutHeader.muscleGroups.adapter =
                MuscleIconAdapter(it.distinct().filter { it != Muscle.BREAK }.take(4))
        }
         */
        return binding.root
    }

}