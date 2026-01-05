package com.example.dentapp.data.remote.model.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleRequest(
    val idToken: String
)