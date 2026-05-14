package com.example.dentapp.domain.usecase.auth

import com.example.dentapp.domain.repository.auth.VerificationRepository
import java.util.UUID
import javax.inject.Inject

class SendVerificationCodeUseCase @Inject constructor(
    private val verificationRepository: VerificationRepository
) {
    suspend operator fun invoke(
        clinicId: UUID,
        fio: String,
        newUserEmail: String,
        clinicEmail: String
    ) {
        return verificationRepository.sendVerificationCode(clinicId, fio, newUserEmail, clinicEmail)
    }
}