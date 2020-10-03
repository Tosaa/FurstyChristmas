package com.example.furstychrismas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furstychrismas.databinding.ExercisePreviewFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ExercisePreview : Fragment() {

    val args: ExercisePreviewArgs by navArgs()
    private val viewModel: ExerciseViewModel by viewModel<ExerciseViewModel> { parametersOf(args.day) }
    private lateinit var binding: ExercisePreviewFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExercisePreviewFragmentBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
        binding.exersices.layoutManager = LinearLayoutManager(requireContext())
        binding.exersices.adapter = ExerciseAdapter(viewModel.exercises)
        binding.muscleGroups.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.muscleGroups.adapter = MuscleIconAdapter(viewModel.muscleGroups.distinct())
        return binding.root
    }

}