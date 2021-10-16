package com.example.furstychristmas.screen.day.workout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychristmas.databinding.MuscleIconBinding
import com.example.furstychristmas.model.Muscle

class MuscleIconAdapter(
    private val muscles: List<Muscle> = emptyList()
) : RecyclerView.Adapter<MuscleIconAdapter.MuscleIconViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuscleIconViewHolder {
        return MuscleIconViewHolder(
            MuscleIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return muscles.size
    }

    override fun onBindViewHolder(holder: MuscleIconViewHolder, position: Int) {
        if (position >= muscles.size) {
            return
        }
            holder.muscleIconBinding.muscleIconId = muscles[position].icon

    }

    class MuscleIconViewHolder(val muscleIconBinding: MuscleIconBinding) :
        RecyclerView.ViewHolder(muscleIconBinding.root)
}