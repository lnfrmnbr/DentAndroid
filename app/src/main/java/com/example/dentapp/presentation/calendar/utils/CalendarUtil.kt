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

        val firstDay = yearMonth.atDay(1)
        val startOffset = (firstDay.dayOfWeek.value % 7)

        val startDate = firstDay.minusDays(startOffset.toLong())

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
        return date.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear())
    }
}