package com.example.furstychristmas.screen.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychristmas.databinding.ExercisePreviewFragmentBinding
import com.example.furstychristmas.model.ExerciseOLD
import com.example.furstychristmas.screen.day.workout.MuscleIconAdapter


class ExercisePreview : Fragment() {
    // private val args: ExercisePreviewArgs by navArgs()
    private lateinit var binding: ExercisePreviewFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExercisePreviewFragmentBinding.inflate(inflater)
        binding.exercise = ExerciseOLD.ABS_ROTATION //args.exercise
        binding.muscleIcons.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.muscleIcons.adapter = MuscleIconAdapter(ExerciseOLD.ABS_ROTATION.muscles)
        return binding.root
    }

}