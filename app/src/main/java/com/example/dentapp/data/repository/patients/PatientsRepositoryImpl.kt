package com.example.dentapp.data.repository.patients

import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.request.patients.PatientRequest
import com.example.dentapp.data.remote.model.response.patients.PatientResponse
import com.example.dentapp.domain.repository.patients.PatientsRepository
import java.util.Date
import javax.inject.Inject

class PatientsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PatientsRepository {
    override suspend fun addPatient(
        fullName: String,
        birthDate: Date,
        sex: String,
        email: String?,
        phoneNumber: String?
    ): PatientResponse {
        return apiService.addPatient(
            PatientRequest(
                fullName = fullName,
                birthDate = birthDate,
                sex = sex,
                email = email,
                phoneNumber = phoneNumber
            )
        )
    }

    override suspend fun getAllPatients(): List<PatientResponse> {
        return apiService.getAllPatients()
    }
}