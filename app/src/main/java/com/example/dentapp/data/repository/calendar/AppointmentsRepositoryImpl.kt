package com.example.dentapp.data.repository.calendar

import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.request.appointment.AppointmentRequest
import com.example.dentapp.data.remote.model.response.calendar.toDomain
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import com.example.dentapp.presentation.periochart.utils.Tooth
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class AppointmentsRepositoryImpl  @Inject constructor(
    private val apiService: ApiService
) : AppointmentsRepository {
    override suspend fun getAppointments(doctorId: UUID?): List<Appointment> {
        return apiService.getAppointments(doctorId!!).map { it.toDomain() }
    }

    override suspend fun getUpcomingAppointments(doctorId: UUID?): List<Appointment> {
        return apiService.getUpcomingAppointments(doctorId!!).map { it.toDomain() }
    }

    override suspend fun getArchiveAppointments(doctorId: UUID?): List<Appointment> {
        return apiService.getArchiveAppointments(doctorId!!).map { it.toDomain() }
    }

    override suspend fun addAppointment(
        doctorId: UUID?,
        patientId: UUID,
        date: LocalDateTime,
        tags: List<String>?,
        diagnosis: List<String>?,
        complaints: String,
        comments: String,
        plan: String,
        isFirst: Boolean,
        indexes: Indexes,
        chart: List<Tooth>?
    ): Appointment {
        return apiService.addAppointment(AppointmentRequest(
            doctorId = doctorId!!,
            patientId = patientId,
            date = date,
            tags = tags,
            diagnosis = diagnosis,
            complaints = complaints,
            comments = comments,
            plan = plan,
            isFirst = isFirst,
            indexes = indexes,
            chart = chart
        )).toDomain()
    }
}