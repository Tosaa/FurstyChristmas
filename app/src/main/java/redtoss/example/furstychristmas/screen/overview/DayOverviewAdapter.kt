package redtoss.example.furstychristmas.screen.overview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.databinding.ChristmasCardBinding
import redtoss.example.furstychristmas.domain.day.model.Day
import redtoss.example.furstychristmas.util.DateUtil
import redtoss.example.furstychristmas.util.NavigationHelper

class DayOverviewAdapter(
    private val navigationHelper: NavigationHelper,
    private var days: List<Day> = emptyList()
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
        val dayIsEnabled = completableDay.day.let { it.isBefore(DateUtil.today()) || it.isEqual(DateUtil.today()) }
        var buttonColor = ContextCompat.getColor(context, R.color.colorLocked)
        var buttonTextColor = ContextCompat.getColor(context, R.color.colorTextLight)
        if (dayIsEnabled) {
            if (completableDay.isDone)
                buttonColor = ContextCompat.getColor(context, R.color.colorCompleted)
            else
                buttonColor = ContextCompat.getColor(context, R.color.colorNotCompleted)
            buttonTextColor = ContextCompat.getColor(context, R.color.colorText)
        } else {
            ContextCompat.getColor(context, R.color.colorLocked)
        }
        holder.binding.button.setBackgroundColor(buttonColor)
        holder.binding.button.setTextColor(buttonTextColor)
        if (dayIsEnabled) {
            holder.binding.button.setOnClickListener {
                navigationHelper.navigateToDay(completableDay.day)
            }
        }
    }

    fun setItems(days: List<Day>) {
        this.days = days
        notifyDataSetChanged()
    }

    inner class CardViewHolder(val binding: ChristmasCardBinding) :
        RecyclerView.ViewHolder(binding.root)
}

