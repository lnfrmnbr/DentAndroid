package com.example.dentapp.domain.usecase.auth

import com.example.dentapp.domain.repository.auth.VerificationRepository
import java.util.UUID
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(
    private val verificationRepository: VerificationRepository
) {
    suspend operator fun invoke(
        clinicId: UUID,
        newUserEmail: String,
        code: String
    ): Boolean {
        return verificationRepository.verifyCode(clinicId, newUserEmail, code)
    }
}