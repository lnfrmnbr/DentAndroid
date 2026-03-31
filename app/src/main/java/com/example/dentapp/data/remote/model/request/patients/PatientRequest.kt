package com.example.dentapp.data.remote.model.request.patients

import com.example.dentapp.utils.serializers.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class PatientRequest (
    val fullName: String,
    @Serializable(with = DateSerializer::class)
    val birthDate: Date,
    val sex: String,
    val email: String?,
    val phoneNumber: String?,
)