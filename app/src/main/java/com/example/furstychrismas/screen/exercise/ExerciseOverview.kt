package com.example.furstychrismas.screen.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furstychrismas.databinding.ExerciseListFragmentBinding
import com.example.furstychrismas.model.Exercise

class ExerciseOverview : Fragment() {

    private lateinit var binding: ExerciseListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExerciseListFragmentBinding.inflate(inflater, container, false)
        binding.exerciseList.layoutManager = LinearLayoutManager(requireContext())
        binding.exerciseList.adapter =
            ExerciseAdapter(Exercise.values().asList(), findNavController())

        return binding.root
    }
}