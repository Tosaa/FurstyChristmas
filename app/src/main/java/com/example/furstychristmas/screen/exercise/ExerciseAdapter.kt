package com.example.furstychristmas.screen.exercise

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychristmas.databinding.ExerciseOverviewItemBinding
import com.example.furstychristmas.model.ExerciseOLD
import com.example.furstychristmas.screen.day.workout.MuscleIconAdapter

class ExerciseAdapter(
    private val exerciseOLDS: List<ExerciseOLD>,
    private val navigationController: NavController
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        context = parent.context
        return ExerciseViewHolder(
            ExerciseOverviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return exerciseOLDS.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        if (position >= exerciseOLDS.size) {
            return
        }
        holder.binding.exerciseName.text = exerciseOLDS[position].exerciseName
        holder.binding.muscleIcons.layoutManager = GridLayoutManager(context, 2)
        holder.binding.muscleIcons.adapter = MuscleIconAdapter(exerciseOLDS[position].muscles)
        holder.binding.item.setOnClickListener {
            navigationController.navigate(ExerciseOverviewDirections.exercisePreview())
        }
    }

    class ExerciseViewHolder(val binding: ExerciseOverviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}