package com.example.furstychristmas.screen.overview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.furstychristmas.R
import com.example.furstychristmas.databinding.ChristmasCardBinding
import com.example.furstychristmas.model.Card

class CardAdapter(
    private val navigationController: NavController,
    private var cards: List<Card> = emptyList()
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: ChristmasCardBinding =
            ChristmasCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        if (position >= cards.size) {
            return
        }
        val card = cards[position]
        holder.binding.day = card.day.dayOfMonth.toString()
        if (card.isAvailable) {
            if (card.isDone)
                holder.binding.button.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.colorGreen)
                )
            else
                holder.binding.button.setBackgroundColor(
                    ContextCompat.getColor(context, R.color.colorPrimaryDark)
                )
        } else {
            holder.binding.button.setBackgroundColor(Color.GRAY)
        }
        if (card.isAvailable) {
            holder.binding.button.setOnClickListener {
                if (card.day.dayOfMonth != 24) {
                    goToWorkoutPreview(position)
                } else
                    goToLastDay()
            }
        }
    }

    fun setItems(cards: List<Card>) {
        this.cards = cards
        notifyDataSetChanged()
    }

    private fun goToLastDay() {
        val action =
            CardsOverviewFragmentDirections.actionCardsOverviewFragmentToLastDay()
        navigationController.navigate(action)
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

