package com.example.dentapp.data.remote.api

import com.example.dentapp.data.remote.model.request.auth.GoogleRequest
import com.example.dentapp.data.remote.model.request.auth.LoginRequest
import com.example.dentapp.data.remote.model.request.auth.LogoutRequest
import com.example.dentapp.data.remote.model.request.auth.RegisterRequest
import com.example.dentapp.data.remote.model.response.auth.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/google")
    suspend fun googleLogin(@Body request: GoogleRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/logout")
    suspend fun logout(@Body request: LogoutRequest): Response<Unit>

    @DELETE("/users/{id}")
    suspend fun deleteUser(@Path("id") userId: String)
}