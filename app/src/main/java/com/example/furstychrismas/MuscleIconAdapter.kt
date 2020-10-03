package com.example.furstychrismas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.databinding.MuscleIconBinding
import com.example.furstychrismas.model.Muscle

class MuscleIconAdapter(
    private val muscles: List<Muscle>
) : RecyclerView.Adapter<MuscleIconAdapter.MuscleIconViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuscleIconViewHolder {
        return MuscleIconViewHolder(MuscleIconBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return muscles.size
    }

    override fun onBindViewHolder(holder: MuscleIconViewHolder, position: Int) {
        holder.muscleIconBinding.muscleIconId = muscles[position].icon
    }

    class MuscleIconViewHolder(val muscleIconBinding: MuscleIconBinding) :
        RecyclerView.ViewHolder(muscleIconBinding.root) {

    }
}