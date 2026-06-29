package com.example.dentapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
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