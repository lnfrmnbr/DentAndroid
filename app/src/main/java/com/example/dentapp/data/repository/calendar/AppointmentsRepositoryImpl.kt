package com.example.dentapp.data.repository.calendar

import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.response.calendar.AppointmentResponse
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
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
}