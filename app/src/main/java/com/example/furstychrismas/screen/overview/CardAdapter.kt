package com.example.furstychrismas.screen.overview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychrismas.databinding.ChristmasCardBinding
import com.example.furstychrismas.model.Card

class CardAdapter(
    private val navigationController: NavController,
    private val cards: List<Card> = emptyList()
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: ChristmasCardBinding =
            ChristmasCardBinding.inflate(LayoutInflater.from(parent.context))
        context = parent.context
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.binding.day = card.day.dayOfMonth.toString()
        if (card.isAvailable) {
            if (card.isDone)
                holder.binding.button.setBackgroundColor(Color.parseColor("#00d000"))
            else
                holder.binding.button.setBackgroundColor(Color.parseColor("#c00000"))
        } else {
            holder.binding.button.setBackgroundColor(Color.GRAY)
        }
        if (card.isAvailable) {
            holder.binding.button.setOnClickListener {
                goToWorkoutPreview(position)
            }
        }
    }

    private fun goToWorkoutPreview(day: Int) {
        val action =
            CardsOverviewFragmentDirections.overviewWorkoutPreview(
                day
            )
        navigationController.navigate(action)
    }

    inner class CardViewHolder(val binding: ChristmasCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}

