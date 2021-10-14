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
import com.example.furstychristmas.model.DayCompleted
import com.example.furstychristmas.util.DateUtil
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DayOverviewAdapter(
    private val navigationController: NavController,
    private var days: List<DayCompleted> = emptyList()
) : RecyclerView.Adapter<DayOverviewAdapter.CardViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding: ChristmasCardBinding =
            ChristmasCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        if (position >= days.size) {
            return
        }
        val completableDay = days[position]
        holder.binding.day = completableDay.day.dayOfMonth.toString()
        val dayIsEnabled = completableDay.day.isBefore(DateUtil.today())
        val buttonColor = if (dayIsEnabled) {
            if (completableDay.isDone)
                ContextCompat.getColor(context, R.color.colorGreen)
            else
                ContextCompat.getColor(context, R.color.colorPrimaryDark)
        } else {
            Color.GRAY
        }
        holder.binding.button.setBackgroundColor(buttonColor)

        if (dayIsEnabled) {
            holder.binding.button.setOnClickListener {
                if (completableDay.day.dayOfMonth != 24) {
                    goToWorkoutPreview(completableDay.day)
                } else
                    goToLastDay()
            }
        }
    }

    fun setItems(dayCompleteds: List<DayCompleted>) {
        this.days = dayCompleteds
        notifyDataSetChanged()
    }

    private fun goToLastDay() {
        val action = CardsOverviewFragmentDirections.actionCardsOverviewFragmentToLastDay()
        navigationController.navigate(action)
    }

    private fun goToWorkoutPreview(date: LocalDate) {
        val action = CardsOverviewFragmentDirections.overviewWorkoutPreview(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
        navigationController.navigate(action)
    }

    inner class CardViewHolder(val binding: ChristmasCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}

