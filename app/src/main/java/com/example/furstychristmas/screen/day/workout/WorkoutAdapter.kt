package com.example.furstychristmas.screen.day.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychristmas.databinding.ExerciseBinding
import com.example.furstychristmas.workout.domain.model.Drill

class WorkoutAdapter(
    private val drills: List<Drill>,
    private val navigationController: NavController
) : RecyclerView.Adapter<WorkoutAdapter.ExerciseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return drills.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        if (position >= drills.size) {
            return
        }
        holder.exerciseBinding.drill = drills[position]
        if (drills[position].exercise.exerciseName != "Pause") {
            holder.exerciseBinding.card.setOnClickListener {
                navigationController.navigate(
                    WorkoutPreviewFragmentDirections.actionWorkoutPreviewToExercisePreview(drills[position].exercise)
                )
            }
        }
    }

    class ExerciseViewHolder(val exerciseBinding: ExerciseBinding) :
        RecyclerView.ViewHolder(exerciseBinding.root)
}