package com.example.dentapp.data.remote.model.request.appointment

import com.example.dentapp.utils.serializers.LocalDateTimeSerializer
import com.example.dentapp.utils.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class AppointmentRequest (
    @Serializable(with = UUIDSerializer::class)
    val doctorId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val patientId: UUID,
    @Serializable(with = LocalDateTimeSerializer::class)
    val date: LocalDateTime,
    val tags: List<String>? = null,
    val bop: Double? = null,
    val russel: Double? = null,
    val api: Double? = null,
    val chart: String? = null,
    val fileUrl: String? = null
)