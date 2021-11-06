package com.example.furstychristmas.screen.exercise

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychristmas.databinding.ExerciseOverviewItemBinding
import com.example.furstychristmas.domain.workout.model.Exercise

class ExerciseAdapter(
    private val exercisesArg: List<Exercise>,
    private val navigationController: NavController
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    private lateinit var context: Context
    private val exercises = exercisesArg.sortedBy { it.exerciseName }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        context = parent.context
        return ExerciseViewHolder(
            ExerciseOverviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        if (position >= exercises.size) {
            return
        }
        holder.binding.exerciseName.text = exercises[position].exerciseName
        // do not show muscles for now (06.11.2021)
        // holder.binding.muscleIcons.layoutManager = GridLayoutManager(context, 2)
        // holder.binding.muscleIcons.adapter = MuscleIconAdapter(exercises[position].muscles)
        holder.binding.item.setOnClickListener {
            navigationController.navigate(ExerciseOverviewDirections.exercisePreview(exercises[position].exerciseId))
        }
    }

    class ExerciseViewHolder(val binding: ExerciseOverviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}