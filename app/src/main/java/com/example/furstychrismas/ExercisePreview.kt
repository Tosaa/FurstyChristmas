package com.example.furstychrismas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.furstychrismas.databinding.ExercisePreviewFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ExercisePreview : Fragment() {

    val args: ExercisePreviewArgs by navArgs()
    private val viewModel:ExerciseViewModel by viewModel<ExerciseViewModel>{ parametersOf(args.day)}
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