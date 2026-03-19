package com.example.dentapp.data.remote.model.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val access: String,
    val refresh: String,
    val userId: String? = null,
    val doctorId: String? = null,
    val email: String? = null,
    val username: String? = null
)