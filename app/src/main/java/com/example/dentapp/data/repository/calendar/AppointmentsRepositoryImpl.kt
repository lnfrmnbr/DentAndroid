package com.example.dentapp.data.repository.calendar

import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.request.appointment.AppointmentRequest
import com.example.dentapp.data.remote.model.response.calendar.AppointmentResponse
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import com.example.dentapp.presentation.periochart.utils.Tooth
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class AppointmentsRepositoryImpl  @Inject constructor(
    private val apiService: ApiService
) : AppointmentsRepository {
    override suspend fun getAppointments(doctorId: UUID): List<AppointmentResponse> {
        return apiService.getAppointments(doctorId)
    }

    override suspend fun getUpcomingAppointments(doctorId: UUID): List<AppointmentResponse> {
        return apiService.getUpcomingAppointments(doctorId)
    }

    override suspend fun getArchiveAppointments(doctorId: UUID): List<AppointmentResponse> {
        return apiService.getArchiveAppointments(doctorId)
    }

    override suspend fun addAppointment(
        doctorId: UUID,
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
    ): AppointmentResponse {
        return apiService.addAppointment(AppointmentRequest(
            doctorId = doctorId,
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
        ))
    }
}