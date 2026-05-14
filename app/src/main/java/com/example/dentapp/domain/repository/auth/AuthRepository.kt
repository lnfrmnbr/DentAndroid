package com.example.dentapp.domain.repository.auth

import com.example.dentapp.data.remote.model.response.auth.AuthResponse
import com.example.dentapp.domain.model.clinic.Clinic
import java.util.Date

interface AuthRepository {
    suspend fun register(
        email: String,
        password: String,
        lastName: String,
        firstName: String,
        patronymic: String?,
        sex: String,
        role: String,
        birthDate: Date,
        clinic: Clinic
    ): Result<AuthResponse>

    suspend fun login(email: String, password: String): Result<AuthResponse>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun logout()
    suspend fun deleteAccount(userId: String)
    suspend fun getAccessToken(): String?
    suspend fun getUserId(): String?
}