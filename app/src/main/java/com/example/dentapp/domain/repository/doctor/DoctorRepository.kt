package com.example.dentapp.domain.repository.doctor

import com.example.dentapp.data.remote.model.response.doctor.DoctorResponse
import java.util.UUID

interface DoctorRepository {
    suspend fun getDoctor(id: UUID): DoctorResponse
    suspend fun getFirstName(): String?
    suspend fun getFullName(): String?
    suspend fun getSex(): String?
}