package com.example.furstychrismas

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.databinding.ChristmasCardBinding

class CardAdapter(
    val layoutInflater: LayoutInflater,
    private val navigationController: NavController
) : RecyclerView.Adapter<CardViewHolder>() {
    val buttons = IntRange(1, 24).toList()
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: ChristmasCardBinding = ChristmasCardBinding.inflate(layoutInflater)
        context = parent.context
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return buttons.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.button.text = buttons[position].toString()
        if (position % 2 == 0)
            holder.binding.button.setBackgroundColor(Color.RED)
        else
            holder.binding.button.setBackgroundColor(Color.GREEN)

        holder.binding.button.setOnClickListener {
            navigationController.navigate(R.id.action_cardsOverviewFragment_to_exersicePreview)
        }
    }
}

class CardViewHolder(val binding: ChristmasCardBinding) : RecyclerView.ViewHolder(binding.root) {

}