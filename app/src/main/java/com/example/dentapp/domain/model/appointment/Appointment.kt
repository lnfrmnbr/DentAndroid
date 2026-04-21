package com.example.dentapp.domain.model.appointment

import com.example.dentapp.domain.model.patients.Patient
import java.time.LocalDateTime

data class Appointment(
    val patient: Patient,
    val date: LocalDateTime = LocalDateTime.now(),
    val tags: List<String>? = emptyList(),
    val indexes: Indexes = Indexes(),
    val complaints: String?= null,
    val comments: String?= null,
    val plan: String? = null,
    val diagnosis: List<String>? = null,
    val chart: String? = null,
    val fileUrl: String? = null
) : java.io.Serializable

data class Indexes(
    val bop: Double = 0.0,
    val api: Double = 0.0,
    val cpitn: List<Int> = listOf(0, 0, 0, 0, 0, 0),
    val russel: Double = 0.0,
    val ohis: Double = 0.0,
    val kpu: Int = 0
) : java.io.Serializable