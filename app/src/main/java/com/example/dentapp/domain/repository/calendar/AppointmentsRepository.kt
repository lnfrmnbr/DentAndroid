package com.example.dentapp.domain.repository.calendar

import com.example.dentapp.data.remote.model.response.calendar.AppointmentResponse
import java.util.UUID


interface AppointmentsRepository {
    suspend fun getAppointments(userId: UUID): List<AppointmentResponse>
}