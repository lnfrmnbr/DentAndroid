package com.example.dentapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dentapp.domain.model.patients.Patient
import java.util.Date
import java.util.UUID

@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val fullName: String,
    val birthDate: Long,
    val sex: Char,
    val email: String? = null,
    val phoneNumber: String? = null
)

fun PatientEntity.toDomain(): Patient {
    return Patient(
        id = id,
        fullName = fullName,
        birthDate = Date(birthDate),
        sex = sex.toString(),
        email = email,
        phoneNumber = phoneNumber
    )
}