package com.example.dentapp.utils

import android.content.Context
import com.example.dentapp.R

object GenderUtils {
    fun toBdCode(sex: String): String {
        return when (sex) {
            "M", "М" -> "M"
            else -> "F"
        }
    }

    fun toAppCode(context: Context, sexBd: String): String {
        return when (sexBd) {
            "M" -> context.getString(R.string.gender_male)
            else -> context.getString(R.string.gender_female)
        }
    }
}