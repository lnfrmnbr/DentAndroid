package com.example.dentapp.di

import com.example.dentapp.data.remote.model.response.auth.AuthResponse
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
        fullName: String,
        firstName: String,
        sex: String,
        role: String,
        birthDate: Date
    ): Result<AuthResponse> {
        if (!isValidEmail(email)) {
            return Result.failure(IllegalArgumentException(AuthErrors.INVALID_EMAIL))
        }

        if (password.length < 6) {
            return Result.failure(IllegalArgumentException(AuthErrors.PASSWORD_TOO_SHORT))
        }

        return authRepository.register(email, password, fullName, firstName, sex, role, birthDate)
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}