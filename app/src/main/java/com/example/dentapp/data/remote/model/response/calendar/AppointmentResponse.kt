package com.example.dentapp.data.remote.model.response.calendar

import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.utils.serializers.LocalDateTimeSerializer
import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

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
    val bop: Double?,
    val russel: Double?,
    val api: Double?,
    val chart: String?,
    val fileUrl: String?
)

fun AppointmentResponse.toDomain(): Appointment = Appointment(
    id = id,
    doctorId = doctorId,
    patientId = patientId,
    patientFullName = patientFullName,
    date = date,
    tags = tags,
    bop = bop,
    russel = russel,
    api = api,
    chart = chart,
    fileUrl = fileUrl,
)