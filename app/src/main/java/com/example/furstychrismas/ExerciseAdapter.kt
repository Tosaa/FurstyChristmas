package com.example.furstychrismas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.databinding.ExerciseBinding
import com.example.furstychrismas.model.Drill

class ExerciseAdapter(
    private val drills: List<Drill>,
    val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(ExerciseBinding.inflate(layoutInflater))
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