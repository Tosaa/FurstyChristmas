package redtoss.example.furstychristmas.screen.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import org.koin.android.ext.android.inject
import redtoss.example.furstychristmas.databinding.ExercisePreviewFragmentBinding
import redtoss.example.furstychristmas.domain.workout.usecase.LoadExerciseUseCase


class ExercisePreview : Fragment() {
    private val args: ExercisePreviewArgs by navArgs()
    private lateinit var binding: ExercisePreviewFragmentBinding
    private val loadExerciseUseCase: LoadExerciseUseCase by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExercisePreviewFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        //binding.exercise = args.exerciseID
        //binding.muscleIcons.adapter = MuscleIconAdapter(ExerciseOLD.ABS_ROTATION.muscles)
        lifecycleScope.launchWhenResumed {
            loadExerciseUseCase.loadExerciseById(args.exerciseID)?.let {
                binding.exercise = it
            }
        }
        return binding.root
    }


}