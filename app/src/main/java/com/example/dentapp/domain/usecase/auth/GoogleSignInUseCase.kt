package com.example.dentapp.domain.usecase.auth

import com.example.dentapp.data.remote.model.response.auth.AuthResponse
import com.example.dentapp.domain.repository.auth.AuthRepository
import com.example.dentapp.domain.usecase.auth.constants.AuthErrors
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<AuthResponse> {
        if (idToken.isBlank()) {
            return Result.failure(IllegalArgumentException(AuthErrors.GOOGLE_TOKEN_EMPTY))
        }

        return authRepository.loginWithGoogle(idToken)
    }
}