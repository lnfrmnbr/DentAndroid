package com.example.dentapp.domain.usecase.patients

import com.example.dentapp.di.RemoteDataSource
import com.example.dentapp.di.LocalDataSource
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.domain.repository.patients.PatientsRepository
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class AddPatientUseCase @Inject constructor(
    @RemoteDataSource private val remoteRepository: PatientsRepository,
    @LocalDataSource private val localRepository: PatientsRepository
) {
    suspend operator fun invoke(
        fullName: String,
        birthDate: Date,
        sex: String,
        email: String?,
        phoneNumber: String?,
        isLocal: Boolean,
        clinicId: UUID? = null
    ): Patient? {
        return if (!isLocal) {
            remoteRepository.addPatient(
                fullName,
                birthDate,
                sex,
                email,
                phoneNumber,
                clinicId
            )
        } else {
            localRepository.addPatient(
                fullName = fullName,
                birthDate = birthDate,
                sex = sex,
                email = email,
                phoneNumber = phoneNumber,
                clinicId = null
            )
        }
    }
}