package com.example.dentapp.domain.usecase.patients

import com.example.dentapp.di.LocalDataSource
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.domain.repository.patients.PatientsRepository
import javax.inject.Inject
import com.example.dentapp.di.RemoteDataSource

class GetAllPatientsUseCase @Inject constructor(
    @RemoteDataSource private val remoteRepository: PatientsRepository,
    @LocalDataSource private val localRepository: PatientsRepository
) {
    suspend operator fun invoke(isLocal: Boolean): List<Patient> = if (!isLocal) {
        remoteRepository.getAllPatients()
    } else {
        localRepository.getAllPatients()
    }
}