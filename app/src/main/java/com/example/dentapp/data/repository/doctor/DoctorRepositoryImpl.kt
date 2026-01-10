package com.example.dentapp.data.repository.doctor

import com.example.dentapp.data.local.AuthDataStore
import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.response.doctor.DoctorResponse
import com.example.dentapp.domain.repository.doctor.DoctorRepository
import java.util.UUID
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStore: AuthDataStore
) : DoctorRepository {
    override suspend fun getDoctor(id: UUID): DoctorResponse {
        return try {
            apiService.getDoctor(id)
        } catch (e: Exception) {
            throw Exception("Ошибка при поиске доктора: ${e.message}")
        }
    }

    override suspend fun getFirstName(): String? {
        return authDataStore.getFirstName()
    }

    override suspend fun getFullName(): String? {
        return authDataStore.getFullName()
    }

    override suspend fun getSex(): String? {
        return authDataStore.getSex()
    }
}