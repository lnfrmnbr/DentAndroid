package com.example.dentapp.domain.repository.calendar

import com.example.dentapp.data.remote.model.response.calendar.AppointmentResponse
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
        tags: List<String>?,
        bop: Double?,
        russel: Double?,
        api: Double?,
        chart: String?,
        fileUrl: String?
    ): AppointmentResponse
}