package com.example.dentapp.utils

import android.content.Context
import java.util.Locale

object LocaleUtils {

    fun isRussianLocale(context: Context): Boolean {
        val currentLocale = getCurrentLocale(context)
        return currentLocale?.language == "ru"
    }

    fun getCurrentLocale(context: Context): Locale? {
        return context.resources.configuration.locales.get(0)
    }
}