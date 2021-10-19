package com.example.furstychristmas.screen.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.furstychristmas.databinding.ExerciseListFragmentBinding
import com.example.furstychristmas.model.ExerciseOLD

class ExerciseOverview : Fragment() {

    private lateinit var binding: ExerciseListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExerciseListFragmentBinding.inflate(inflater, container, false)
        binding.exerciseList.apply {

            layoutManager = LinearLayoutManager(requireContext())
            adapter = ExerciseAdapter(
                ExerciseOLD.values().filter { it.exerciseName != "Pause" },
                findNavController()
            )
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        return binding.root
    }
}