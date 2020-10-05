package com.example.furstychrismas.screen.day

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.databinding.ExerciseBinding
import com.example.furstychrismas.model.Drill

class ExerciseAdapter(
    private val drills: List<Drill>
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ExerciseBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
    return drills.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.exerciseBinding.drill = drills[position]
    }

    class ExerciseViewHolder(val exerciseBinding: ExerciseBinding) :
        RecyclerView.ViewHolder(exerciseBinding.root) {

    }
}