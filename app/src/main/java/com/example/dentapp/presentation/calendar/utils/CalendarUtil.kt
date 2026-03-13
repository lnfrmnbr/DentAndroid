package com.example.dentapp.presentation.calendar.utils

import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.Locale

object CalendarUtil {
    fun generateMonthDays(
        yearMonth: YearMonth,
        events: List<CalendarEvent>
    ): List<List<CalendarDay>> {

        val firstDayOfMonth = yearMonth.atDay(1)

        val startOffset = firstDayOfMonth.dayOfWeek.value - 1

        val startDate = firstDayOfMonth.minusDays(startOffset.toLong())

        val today = LocalDate.now()

        return (0 until 6).map { week ->
            (0 until 7).map { day ->

                val date = startDate.plusDays((week * 7 + day).toLong())

                CalendarDay(
                    date = date,
                    isCurrentMonth = date.month == yearMonth.month,
                    isToday = date == today,
                    events = events.filter { it.date == date }
                )
            }
        }
    }

    fun weekNumber(date: LocalDate): Int {
        val weekFields = WeekFields.of(Locale.getDefault())
        return date.get(weekFields.weekOfWeekBasedYear())
    }
}