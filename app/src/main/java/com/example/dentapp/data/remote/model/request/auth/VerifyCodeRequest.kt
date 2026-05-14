package com.example.dentapp.data.remote.model.request.auth

import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class VerifyCodeRequest(
    @Serializable(with = UUIDSerializer::class)
    val clinicId: UUID,
    val newUserEmail: String,
    val code: String
)
