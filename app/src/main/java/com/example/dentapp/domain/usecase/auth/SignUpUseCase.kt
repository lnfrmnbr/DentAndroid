package com.example.dentapp.domain.usecase.auth

import android.util.Patterns
import com.example.dentapp.data.remote.model.response.auth.AuthResponse
import com.example.dentapp.domain.model.clinic.Clinic
import com.example.dentapp.domain.repository.auth.AuthRepository
import com.example.dentapp.domain.usecase.auth.constants.AuthErrors
import java.util.Date
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        lastName: String,
        firstName: String,
        patronymic: String?,
        sex: String,
        role: String,
        birthDate: Date,
        clinic: Clinic
    ): Result<AuthResponse> {
        if (!isValidEmail(email)) {
            return Result.failure(IllegalArgumentException(AuthErrors.INVALID_EMAIL))
        }

        if (password.length < 6) {
            return Result.failure(IllegalArgumentException(AuthErrors.PASSWORD_TOO_SHORT))
        }

        return authRepository.register(email, password, lastName, firstName, patronymic, sex, role, birthDate, clinic)
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}