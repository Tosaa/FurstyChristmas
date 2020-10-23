package com.example.furstychrismas.screen.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.R
import com.example.furstychrismas.databinding.ExerciseListFragmentBinding
import com.example.furstychrismas.model.Exercise
import kotlinx.android.synthetic.main.exercise.view.*

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
            adapter = ExerciseAdapter(Exercise.values().asList(), findNavController())
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