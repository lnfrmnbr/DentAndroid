package com.example.dentapp.utils

object GenderUtils {
    fun toBdCode(sex: String): String {
        return when (sex) {
            "M", "М" -> "M"
            else -> "F"
        }
    }
}