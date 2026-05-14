package com.example.dentapp.domain.repository.auth

import java.util.UUID

interface VerificationRepository {
    suspend fun sendVerificationCode(
        clinicId: UUID,
        fio: String,
        newUserEmail: String,
        clinicEmail: String
    )

    suspend fun verifyCode(clinicId: UUID, newUserEmail: String, code: String): Boolean
}