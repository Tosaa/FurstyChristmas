package com.example.furstychrismas.repository

import com.example.furstychrismas.model.*
import com.example.furstychrismas.persistence.CardDao
import com.example.furstychrismas.persistence.CardDatabase
import com.example.furstychrismas.util.Util
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class DayRepository(db: CardDatabase) {

    private val cardDao = db.cardDao()

    private val cards = IntRange(1, 24).map {
        Card(Util.intToDayInDecember(it), false)
    }

    val cardsld = cardDao.getCards()

    suspend fun createCards() {

    }

    fun getWorkoutOfDay(day: Int): Workout {

        if (cardsld.value?.any { card -> card.day == Util.intToDayInDecember(day) } == true) {
            return Workout(day, chestWorkout(), 4)
        }

        val date = Calendar.Builder().apply {
            set(Calendar.MONTH, Calendar.DECEMBER)
            set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))
            set(Calendar.DAY_OF_MONTH, day)
        }.build()

        var sets = 3 + date.get(Calendar.WEEK_OF_MONTH)

        val drills = mutableListOf<Drill>()
        if (date.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            sets = 3
            drills.addAll(chestWorkout())
        } else {
            drills.addAll(generateDrills(date))
        }

        //even days -> legs
        // odd days -> arms
        // mondays -> Stretch
        // Tuesdays -> extra set
        // Wednesday -> Back
        return Workout(day, drills.filterNotNull(), sets)
    }


    private fun generateDrills(date: Calendar): List<Drill> {
        val dayBasedTask = when (date.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> Drill(Repetition(5), Exercise.SQUAD, Seconds(5))
            Calendar.TUESDAY -> Drill(Repetition(5), Exercise.PUSHUP, Seconds(5))
            else -> null
        }

        val dailyStandardTask = if (date.get(Calendar.DAY_OF_MONTH) % 2 == 0) {
            Drill(Repetition(5), Exercise.PUSHUP, Seconds(5))
        } else {
            Drill(Repetition(5), Exercise.SQUAD, Seconds(5))
        }
        return listOf(dayBasedTask, dailyStandardTask).filterNotNull()
    }

    private fun chestWorkout(): List<Drill> {
        return listOf<Drill>(
            Drill(Repetition(5), Exercise.PUSHUP, Seconds(0)),
            Drill(Repetition(5), Exercise.PUSHUP_TIGHT, Seconds(0)),
            Drill(Repetition(5), Exercise.PUSHUP_WIDE, Seconds(0)),
            Drill(Repetition(10), Exercise.PUSHUP_TO_PLANK, Seconds(20)),
            Drill(Repetition(5), Exercise.MILITARY, Seconds(0)),
            Drill(Seconds(30), Exercise.PLANK, Seconds(0)),
            Drill(Seconds(30), Exercise.PLANK_SIDE, Seconds(0)),
            Drill(Seconds(30), Exercise.PLANK, Seconds(0)),
            Drill(Seconds(30), Exercise.PLANK_SIDE, Seconds(20))
        )
    }
}