package com.example.dentapp.data.remote.model.request.auth

import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class GetVerificationCodeRequest(
    @Serializable(with = UUIDSerializer::class)
    val clinicId: UUID,
    val fio: String,
    val newUserEmail: String,
    val clinicEmail: String
)
