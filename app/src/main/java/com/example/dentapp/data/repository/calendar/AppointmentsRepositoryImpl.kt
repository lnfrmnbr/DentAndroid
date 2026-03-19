package com.example.dentapp.data.repository.calendar

import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.data.remote.model.response.calendar.AppointmentResponse
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import java.util.UUID
import javax.inject.Inject

class AppointmentsRepositoryImpl  @Inject constructor(
    private val apiService: ApiService
) : AppointmentsRepository {
    override suspend fun getAppointments(userId: UUID): List<AppointmentResponse> {
        return apiService.getAppointments(userId)
    }

}