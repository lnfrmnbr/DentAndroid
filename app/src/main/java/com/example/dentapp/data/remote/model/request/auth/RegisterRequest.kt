package com.example.dentapp.data.remote.model.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val firstName: String,
    val sex: String
)