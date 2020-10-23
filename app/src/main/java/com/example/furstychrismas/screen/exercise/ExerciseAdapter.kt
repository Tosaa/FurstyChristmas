package com.example.furstychrismas.screen.exercise

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.databinding.ExerciseOverviewItemBinding
import com.example.furstychrismas.model.Exercise
import com.example.furstychrismas.screen.day.MuscleIconAdapter

class ExerciseAdapter(
    private val exercises: List<Exercise>,
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
        return exercises.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.binding.exerciseName.text = exercises[position].exerciseName
        holder.binding.muscleIcons.layoutManager = GridLayoutManager(context, 2)
        holder.binding.muscleIcons.adapter = MuscleIconAdapter(exercises[position].muscles)
        holder.binding.item.setOnClickListener {
            navigationController.navigate(ExerciseOverviewDirections.exercisePreview(exercises[position]))
        }
    }

    class ExerciseViewHolder(val binding: ExerciseOverviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}