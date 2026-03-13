package com.example.dentapp.presentation.calendar.utils

import com.example.dentapp.domain.model.calendar.Appointment
import java.time.LocalDate

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isSelected: Boolean,
    val isToday: Boolean,
    val events: List<Appointment>
)