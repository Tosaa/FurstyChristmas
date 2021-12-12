package redtoss.example.furstychristmas.screen.christmas

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import redtoss.example.furstychristmas.databinding.HistoryItemBinding

class HistoryAdapter(
    exercises: Map<String, String> = emptyMap()
) : RecyclerView.Adapter<HistoryAdapter.ExerciseViewHolder>() {
    private lateinit var context: Context
    private var drills = exercises.keys
    private var amounts = exercises.values

    fun updateItems(map: Map<String, String>) {
        drills = map.keys
        amounts = map.values
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        context = parent.context
        return ExerciseViewHolder(
            HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return drills.size
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        if (position >= drills.size) {
            return
        }
        holder.binding.drill = drills.elementAt(position)
        holder.binding.amount = amounts.elementAt(position)

    }

    class ExerciseViewHolder(val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}