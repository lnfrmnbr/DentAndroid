package com.example.dentapp.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateUtils {
    fun toDMMMM(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
        return date.format(formatter)
    }

    fun toHHMM(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return date.format(formatter)
    }
}