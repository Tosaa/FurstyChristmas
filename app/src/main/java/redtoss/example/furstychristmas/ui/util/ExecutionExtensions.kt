package redtoss.example.furstychristmas.ui.util

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.calendar.content.info.InfoPageContent
import redtoss.example.furstychristmas.calendar.content.workout.Execution
import redtoss.example.furstychristmas.calendar.content.workout.Muscle
import timber.log.Timber

fun Execution.formattedString(): SpannableStringBuilder = SpannableStringBuilder("")
    .bold { append(amount.toString()) }
    .append(" ${unit()}")

val Muscle.icon: Int
    get() = this.iconId.asIconId()

private fun String.asIconId(): Int {
    return when (this) {
        "ic_legs" -> R.drawable.ic_legs
        "ic_arm" -> R.drawable.ic_arm
        "ic_chest" -> R.drawable.ic_chest
        "ic_abs" -> R.drawable.ic_abs
        "ic_back" -> R.drawable.ic_back
        "ball" -> R.drawable.ball
        "ic_flex" -> R.drawable.ic_flex
        "ic_timer" -> R.drawable.ic_timer
        else ->
            0
    }
}