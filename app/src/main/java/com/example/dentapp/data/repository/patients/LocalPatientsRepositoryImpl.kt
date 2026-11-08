package com.example.dentapp.data.repository.patients

import com.example.dentapp.data.local.dao.PatientDao
import com.example.dentapp.data.local.entity.PatientEntity
import com.example.dentapp.data.local.entity.toDomain
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.domain.repository.patients.PatientsRepository
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class LocalPatientsRepositoryImpl @Inject constructor(
    private val patientDao: PatientDao
): PatientsRepository {

    override suspend fun addPatient(
        fullName: String,
        birthDate: Date,
        sex: String,
        email: String?,
        phoneNumber: String?,
        clinicId: UUID?
    ): Patient? {
        val patient = PatientEntity(
            fullName = fullName,
            birthDate = birthDate.time,
            sex = sex.first(),
            email = email,
            phoneNumber = phoneNumber
        )
        patientDao.insertPatient(patient)
        return getPatientById(patient.id)?.toDomain()
    }

    override suspend fun getAllPatients(): List<Patient> {
        return patientDao.getAllPatients().map { it.toDomain() }
    }

    suspend fun getPatientById(id: UUID): PatientEntity? {
        return patientDao.getPatientById(id)
    }
}