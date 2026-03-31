package com.example.dentapp.domain.usecase.patients

import com.example.dentapp.data.remote.model.response.patients.toDomain
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.domain.repository.patients.PatientsRepository
import javax.inject.Inject

class GetAllPatientsUseCase @Inject constructor(
    private val repository: PatientsRepository
) {
    suspend operator fun invoke(): List<Patient> = repository.getAllPatients().map { it.toDomain() }
}