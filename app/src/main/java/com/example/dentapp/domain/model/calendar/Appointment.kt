package com.example.dentapp.domain.model.calendar

import java.time.LocalDateTime
import java.util.UUID

data class Appointment(
    val id: UUID? = null,
    val doctorId: UUID,
    val patientId: UUID,
    val patientFullName: String? = null,
    val date: LocalDateTime = LocalDateTime.now(),
    val tags: List<String>? = emptyList(),
    val bop: Double? = null,
    val russel: Double? = null,
    val api: Double? = null,
    val chart: String? = null,
    val fileUrl: String? = null
)