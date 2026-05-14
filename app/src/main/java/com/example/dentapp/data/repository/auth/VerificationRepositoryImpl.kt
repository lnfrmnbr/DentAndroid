package com.example.dentapp.data.repository.auth

import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.request.auth.GetVerificationCodeRequest
import com.example.dentapp.data.remote.model.request.auth.VerifyCodeRequest
import com.example.dentapp.domain.repository.auth.VerificationRepository
import java.util.UUID
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : VerificationRepository{
    override suspend fun sendVerificationCode(
        clinicId: UUID,
        fio: String,
        newUserEmail: String,
        clinicEmail: String
    ) {
        return apiService.sendVerificationCode(
            GetVerificationCodeRequest(
                clinicId = clinicId,
                fio = fio,
                newUserEmail = newUserEmail,
                clinicEmail = clinicEmail
            )
        )
    }

    override suspend fun verifyCode(
        clinicId: UUID,
        newUserEmail: String,
        code: String
    ): Boolean {
        return apiService.verifyCode(
            VerifyCodeRequest(
                clinicId = clinicId,
                newUserEmail = newUserEmail,
                code = code
            )
        )
    }
}