package com.example.dentapp.domain.repository.calendar

import com.example.dentapp.data.remote.model.response.calendar.AppointmentResponse
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.presentation.periochart.utils.Tooth
import java.time.LocalDateTime
import java.util.UUID


interface AppointmentsRepository {
    suspend fun getAppointments(doctorId: UUID): List<AppointmentResponse>
    suspend fun getUpcomingAppointments(doctorId: UUID): List<AppointmentResponse>
    suspend fun getArchiveAppointments(doctorId: UUID): List<AppointmentResponse>
    suspend fun addAppointment(
        doctorId: UUID,
        patientId: UUID,
        date: LocalDateTime,
        tags: List<String>? = null,
        diagnosis: List<String>? = null,
        complaints: String,
        comments: String,
        plan: String,
        isFirst: Boolean,
        indexes: Indexes,
        chart: List<Tooth>?
    ): AppointmentResponse
}