package com.example.dentapp.data.remote.model.response.calendar

import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.presentation.periochart.utils.Tooth
import com.example.dentapp.utils.serializers.LocalDateTimeSerializer
import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID
import kotlin.String

@Serializable
data class AppointmentResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    @Serializable(with = UUIDSerializer::class)
    val doctorId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val patientId: UUID,
    val patientFullName: String?,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime,
    val tags: List<String>?,
    val diagnosis: List<String>?,
    val complaints: String,
    val comments: String,
    val plan: String,
    val isFirst: Boolean,
    val indexes: Indexes,
    val chart: List<Tooth>?
)

fun AppointmentResponse.toDomain(): Appointment = Appointment(
    id = id,
    doctorId = doctorId,
    patientId = patientId,
    patientFullName = patientFullName,
    date = date,
    tags = tags,
    diagnosis = diagnosis,
    complaints = complaints,
    comments = comments,
    plan = plan,
    isFirst = isFirst,
    indexes = indexes,
    chart = chart
)