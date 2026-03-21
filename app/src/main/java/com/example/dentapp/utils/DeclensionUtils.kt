package com.example.dentapp.utils

object DeclensionUtils {

    fun appointmentDeclension(number: Int): String {
        return when {
            number % 10 == 1 && number % 100 != 11 -> "запись"
            number % 10 in 2..4 && (number % 100 < 10 || number % 100 > 20) -> "записи"
            else -> "записей"
        }
    }
}