package com.example.dentapp.data.remote.model.request.auth

import com.example.dentapp.utils.serializers.DateSerializer
import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val lastName: String,
    val firstName: String,
    val patronymic: String?,
    val sex: String,
    val role: String,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date,
    @Serializable(with = UUIDSerializer::class)
    val clinicId: UUID
)