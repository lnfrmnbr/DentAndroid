package com.example.dentapp.data.remote.model.response.patients

import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.utils.serializers.DateSerializer
import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

@Serializable
data class PatientResponse (
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val clinicId: UUID?,
    val fullName: String,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date,
    val sex: String,
    val email: String?,
    val phoneNumber: String?,
)

fun PatientResponse.toDomain(): Patient = Patient(
    id = id,
    fullName = fullName,
    birthDate = birthDate,
    sex = sex,
    email = email,
    phoneNumber = phoneNumber,
    clinicId = clinicId
)