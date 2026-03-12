package com.example.dentapp.presentation.calendar.utils

import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
    val events: List<CalendarEvent>
)