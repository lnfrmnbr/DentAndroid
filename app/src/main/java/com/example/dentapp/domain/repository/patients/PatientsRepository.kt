package com.example.dentapp.domain.repository.patients

import com.example.dentapp.data.remote.model.response.patients.PatientResponse
import java.util.Date

interface PatientsRepository {
    suspend fun addPatient(fullName: String, birthDate: Date, sex:String, email: String?, phoneNumber: String?): PatientResponse
    suspend fun getAllPatients(): List<PatientResponse>
}