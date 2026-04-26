package com.example.dentapp.domain.model.calendar

import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.presentation.periochart.utils.Tooth
import java.time.LocalDateTime
import java.util.UUID

data class Appointment(
    val id: UUID? = null,
    val doctorId: UUID,
    val patientId: UUID,
    val patientFullName: String? = null,
    val date: LocalDateTime = LocalDateTime.now(),
    val tags: List<String>? = emptyList(),
    val diagnosis: List<String>? = emptyList(),
    val complaints: String? = null,
    val comments: String? = null,
    val plan: String? = null,
    val isFirst: Boolean? = null,
    val indexes: Indexes? = null,
    val chart: List<Tooth>? = null
)