package com.example.dentapp.domain.usecase.clinic

import com.example.dentapp.data.remote.model.response.clinic.toDomain
import com.example.dentapp.domain.model.clinic.Clinic
import com.example.dentapp.domain.repository.clinic.ClinicRepository
import javax.inject.Inject

class GetAllClinicsUseCase @Inject constructor(
    private val repository: ClinicRepository
) {
    suspend operator fun invoke(): List<Clinic> = repository.getAllClinics().map { it.toDomain() }
}