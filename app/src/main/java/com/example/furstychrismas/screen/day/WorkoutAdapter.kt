package com.example.furstychrismas.screen.day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.databinding.ExerciseBinding
import com.example.furstychrismas.model.Drill

class WorkoutAdapter(
    private val drills: List<Drill>,
    private val navigationController: NavController
) : RecyclerView.Adapter<WorkoutAdapter.ExerciseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ExerciseBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return drills.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.exerciseBinding.drill = drills[position]
        holder.exerciseBinding.breakTime.visibility =
            if (drills[position].breakTime.formattedString().startsWith("0")) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        holder.exerciseBinding.card.setOnClickListener { navigationController.navigate(WorkoutPreviewDirections.actionWorkoutPreviewToExercisePreview(drills[position].exercise)) }
    }

    class ExerciseViewHolder(val exerciseBinding: ExerciseBinding) :
        RecyclerView.ViewHolder(exerciseBinding.root)
}