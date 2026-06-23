package com.example.dentapp.domain.usecase.auth

import com.example.dentapp.domain.repository.auth.AuthRepository
import javax.inject.Inject

class SignInOfflineUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() {
        return authRepository.offlineLogin()
    }
}