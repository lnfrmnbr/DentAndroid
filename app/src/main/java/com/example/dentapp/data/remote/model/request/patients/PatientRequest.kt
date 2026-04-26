package com.example.dentapp.data.remote.model.request.patients

import com.example.dentapp.utils.serializers.DateSerializer
import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

@Serializable
data class PatientRequest (
    @Serializable(with = UUIDSerializer::class)
    val clinicId: UUID,
    val fullName: String,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date,
    val sex: String,
    val email: String?,
    val phoneNumber: String?
)