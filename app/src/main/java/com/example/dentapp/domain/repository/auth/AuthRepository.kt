package com.example.dentapp.domain.repository.auth

import com.example.dentapp.data.remote.model.response.auth.AuthResponse

interface AuthRepository {
    suspend fun register(email: String, password: String, fullName: String, firstName: String, sex: String): Result<AuthResponse>
    suspend fun login(email: String, password: String): Result<AuthResponse>
    suspend fun loginWithGoogle(idToken: String): Result<AuthResponse>
    suspend fun isUserLoggedIn(): Boolean
    suspend fun logout()
    suspend fun deleteAccount(userId: String)
    suspend fun getAccessToken(): String?
    suspend fun getUserId(): String?
}