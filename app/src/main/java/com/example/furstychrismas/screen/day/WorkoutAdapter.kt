package com.example.furstychrismas.screen.day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.databinding.ExerciseBinding
import com.example.furstychrismas.model.Drill

class WorkoutAdapter(
    private val drills: List<Drill>
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
            if (drills[position].breakTime.formatedString().startsWith("0")) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
    }

    class ExerciseViewHolder(val exerciseBinding: ExerciseBinding) :
        RecyclerView.ViewHolder(exerciseBinding.root)
}