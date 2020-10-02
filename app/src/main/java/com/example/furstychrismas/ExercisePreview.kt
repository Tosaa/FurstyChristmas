package com.example.furstychrismas

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.furstychrismas.databinding.ExercisePreviewFragmentBinding

class ExercisePreview : Fragment() {

    private val viewModel by viewModels<ExerciseViewModel>()
    private lateinit var binding : ExercisePreviewFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExercisePreviewFragmentBinding.inflate(layoutInflater)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ExerciseViewModel::class.java)
    }

}