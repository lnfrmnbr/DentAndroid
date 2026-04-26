package com.example.dentapp.data.remote.model.request.appointment

import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.presentation.periochart.utils.Tooth
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
    val diagnosis: List<String>? = null,
    val complaints: String,
    val comments: String,
    val plan: String,
    val isFirst: Boolean,
    val indexes: Indexes,
    val chart: List<Tooth>?
)