package com.example.dentapp.domain.usecase.patients

import com.example.dentapp.data.remote.model.response.patients.toDomain
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.domain.repository.patients.PatientsRepository
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class AddPatientUseCase @Inject constructor(
    private val repository: PatientsRepository
) {
    suspend operator fun invoke(
        fullName: String,
        birthDate: Date,
        sex: String,
        email: String?,
        phoneNumber: String?,
        clinicId: UUID
    ): Patient {
        return repository.addPatient(
            fullName,
            birthDate,
            sex,
            email,
            phoneNumber,
            clinicId
        ).toDomain()
    }
}