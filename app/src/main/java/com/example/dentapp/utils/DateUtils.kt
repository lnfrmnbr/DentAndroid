package com.example.dentapp.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
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

    fun toDDMMYYYY(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return date.format(formatter)
    }

    fun toDDMMYYYY(date: Date): String {
        return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
    }

    fun countAge(date: Date): Int {
        val birthDate = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return Period.between(birthDate, LocalDate.now()).years
    }
}