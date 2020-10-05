package com.example.furstychrismas.screen.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.databinding.ExercisePreviewFragmentBinding
import com.example.furstychrismas.screen.day.MuscleIconAdapter


class ExercisePreview : Fragment() {
    private val args: ExercisePreviewArgs by navArgs()
    private lateinit var binding: ExercisePreviewFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExercisePreviewFragmentBinding.inflate(inflater)
        binding.exercise = args.exercise
        binding.muscleIcons.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.muscleIcons.adapter = MuscleIconAdapter(args.exercise.muscles)
        return binding.root
    }

}