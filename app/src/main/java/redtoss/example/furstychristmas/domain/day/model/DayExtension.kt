package redtoss.example.furstychristmas.domain.day.model

import java.time.LocalDate

fun Day.isAvailableOn(date: LocalDate): Boolean {
    return date.year == this.day.year && date.dayOfYear == this.day.dayOfYear || this.day.isBefore(date)
}