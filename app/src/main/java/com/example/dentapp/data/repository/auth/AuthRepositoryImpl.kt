package com.example.dentapp.data.repository.auth


import com.example.dentapp.data.local.AuthDataStore
import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.request.auth.GoogleRequest
import com.example.dentapp.data.remote.model.request.auth.LoginRequest
import com.example.dentapp.data.remote.model.request.auth.LogoutRequest
import com.example.dentapp.data.remote.model.request.auth.RegisterRequest
import com.example.dentapp.data.remote.model.response.auth.AuthResponse
import com.example.dentapp.domain.repository.auth.AuthRepository
import com.example.dentapp.utils.decoders.JwtDecoder
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStore: AuthDataStore
) : AuthRepository {

    override suspend fun register(
        email: String,
        password: String,
        fullName: String,
        firstName: String,
        sex: String
    ): Result<AuthResponse> {
        return try {
            val response = apiService.register(RegisterRequest(email, password, fullName, firstName, sex))
            if (response.isSuccessful) {
                val authResponse =
                    response.body() ?: return Result.failure(Exception("Пустой ответ от сервера"))

                val userId = JwtDecoder.decodeUserId(authResponse.access)
                    ?: return Result.failure(Exception("Не удалось расшифровать ID пользователя из токена"))

                authDataStore.saveAuthData(
                    isLoggedIn = true,
                    accessToken = authResponse.access,
                    refreshToken = authResponse.refresh,
                    userId = userId,
                    email = email,
                    fullName = fullName,
                    firstName = firstName,
                    sex = sex
                )
                Result.success(authResponse)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Неизвестная ошибка"
                Result.failure(Exception("Ошибка регистрации: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка сети: ${e.message}"))
        }
    }

    override suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                val authResponse =
                    response.body() ?: return Result.failure(Exception("Пустой ответ от сервера"))

                val userId = JwtDecoder.decodeUserId(authResponse.access)
                    ?: return Result.failure(Exception("Не удалось расшифровать ID пользователя из токена"))

                val doctor = apiService.getDoctor(UUID.fromString(userId))

                authDataStore.saveAuthData(
                    isLoggedIn = true,
                    accessToken = authResponse.access,
                    refreshToken = authResponse.refresh,
                    userId = userId,
                    email = email,
                    fullName = doctor.fullName,
                    firstName = doctor.firstName,
                    sex = doctor.sex
                )
                Result.success(authResponse)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Неизвестная ошибка"
                Result.failure(Exception("Ошибка входа: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка сети: ${e.message}"))
        }
    }

    override suspend fun loginWithGoogle(idToken: String): Result<AuthResponse> {
        return try {
            val response = apiService.googleLogin(GoogleRequest(idToken))
            if (response.isSuccessful) {
                val authResponse =
                    response.body() ?: return Result.failure(Exception("Пустой ответ от сервера"))

                val userId = JwtDecoder.decodeUserId(authResponse.access)
                    ?: return Result.failure(Exception("Не удалось расшифровать ID пользователя из токена"))

                authDataStore.saveAuthData(
                    isLoggedIn = true,
                    accessToken = authResponse.access,
                    refreshToken = authResponse.refresh,
                    userId = userId,
                    email = authResponse.email
                )
                Result.success(authResponse)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Неизвестная ошибка"
                Result.failure(Exception("Ошибка входа через Google: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка сети: ${e.message}"))
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return authDataStore.isLoggedIn.first()
    }

    override suspend fun logout() {
        try {
            val refreshToken = authDataStore.getRefreshTokenBlocking()
            refreshToken?.let {
                apiService.logout(LogoutRequest(it))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            authDataStore.clearAuthData()
        }
    }

    override suspend fun deleteAccount(userId: String) {
        return try {
            logout()
            apiService.deleteUser(userId)
            authDataStore.clearAuthData()
        } catch (e: Exception) {
            throw Exception("Ошибка при удалении аккаунта: ${e.message}")
        }
    }

    override suspend fun getAccessToken(): String? {
        return authDataStore.getAccessToken()
    }

    override suspend fun getUserId(): String? {
        return authDataStore.getUserId()
    }
}