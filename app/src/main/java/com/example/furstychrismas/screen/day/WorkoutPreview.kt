package com.example.furstychrismas.screen.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furstychrismas.databinding.WorkoutPreviewFragmentBinding
import kotlinx.android.synthetic.main.warning_dialog.view.*
import org.koin.android.ext.android.bind
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

        binding.warningDialog.cancelButton.setOnClickListener {
            binding.warningDialog.root.visibility = View.GONE
        }
        binding.workoutDoneButton.setOnClickListener {
            viewModel.markAsDone()
        }
        viewModel.exercises.observe(viewLifecycleOwner) { drills ->
            binding.exercises.adapter = WorkoutAdapter(drills)
        }

        viewModel.muscleGroups.observe(viewLifecycleOwner) {
            binding.workoutHeader.muscleGroups.adapter =
                MuscleIconAdapter(it.distinct().take(4))
        }
        return binding.root
    }

}