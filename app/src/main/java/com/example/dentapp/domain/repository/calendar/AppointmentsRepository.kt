package com.example.dentapp.domain.repository.calendar

import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.presentation.periochart.utils.Tooth
import java.time.LocalDateTime
import java.util.UUID


interface AppointmentsRepository {
    suspend fun getAppointments(doctorId: UUID? = null): List<Appointment>
    suspend fun getUpcomingAppointments(doctorId: UUID? = null): List<Appointment>
    suspend fun getArchiveAppointments(doctorId: UUID? = null): List<Appointment>
    suspend fun addAppointment(
        doctorId: UUID? = null,
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
    ): Appointment?
}