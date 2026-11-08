package com.example.dentapp.data.repository.patients

import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.request.patients.PatientRequest
import com.example.dentapp.data.remote.model.response.patients.toDomain
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.domain.repository.patients.PatientsRepository
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class PatientsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PatientsRepository {
    override suspend fun addPatient(
        fullName: String,
        birthDate: Date,
        sex: String,
        email: String?,
        phoneNumber: String?,
        clinicId: UUID?
    ): Patient {
        return apiService.addPatient(
            PatientRequest(
                fullName = fullName,
                birthDate = birthDate,
                sex = sex,
                email = email,
                phoneNumber = phoneNumber,
                clinicId = clinicId!!
            )
        ).toDomain()
    }

    override suspend fun getAllPatients(): List<Patient> {
        return apiService.getAllPatients().map { it.toDomain() }
    }
}