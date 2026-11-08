package com.example.dentapp.domain.repository.patients

import com.example.dentapp.domain.model.patients.Patient
import java.util.Date
import java.util.UUID

interface PatientsRepository {

    suspend fun addPatient(
        fullName: String,
        birthDate: Date,
        sex: String,
        email: String?,
        phoneNumber: String?,
        clinicId: UUID? = null
    ): Patient?

    suspend fun getAllPatients(): List<Patient>
}