package com.example.dentapp.data.repository.clinic

import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.response.clinic.ClinicResponse
import com.example.dentapp.domain.repository.clinic.ClinicRepository
import javax.inject.Inject

class ClinicRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ClinicRepository {
    override suspend fun getAllClinics(): List<ClinicResponse> {
         return apiService.getAllClinics()
    }
}