package com.example.dentapp.domain.usecase.auth

import com.example.dentapp.domain.usecase.auth.constants.AuthErrors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class ValidateDateUseCase @Inject constructor() {

    operator fun invoke(dateStr: String): ValidationResult {

        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
            isLenient = false
        }

        if (dateStr.length < 10) {
            return ValidationResult(
                successful = false,
                errorMessage = AuthErrors.SHORT_DATE
            )
        }

        val date: Date = try {
            formatter.parse(dateStr)!!
        } catch (e: Exception) {
            return ValidationResult(
                successful = false,
                errorMessage = AuthErrors.UNREAL_DATE
            )
        }

        if (date.after(Date())) {
            return ValidationResult(
                successful = false,
                errorMessage = AuthErrors.UNREAL_DATE
            )
        }

        return ValidationResult(successful = true)
    }
}