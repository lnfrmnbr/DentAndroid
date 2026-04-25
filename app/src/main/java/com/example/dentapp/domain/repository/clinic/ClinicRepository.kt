package com.example.dentapp.domain.repository.clinic

import com.example.dentapp.data.remote.model.response.clinic.ClinicResponse

interface ClinicRepository {
    suspend fun getAllClinics(): List<ClinicResponse>
}