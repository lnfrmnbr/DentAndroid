package com.example.dentapp.data.remote.model.request.auth

import com.example.dentapp.utils.serializers.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val firstName: String,
    val sex: String,
    val role: String,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date
)