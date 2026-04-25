package com.example.dentapp.domain.model.patients

import java.util.Date
import java.util.UUID

data class Patient(
    val id: UUID? = null,
    val fullName: String,
    val birthDate: Date,
    val sex: String,
    val email: String? = null,
    val phoneNumber: String? = null,
    val clinicId: UUID? = null
) : java.io.Serializable