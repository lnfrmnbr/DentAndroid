package com.example.dentapp.domain.repository.calendar

import com.example.dentapp.data.remote.model.response.calendar.AppointmentResponse
import java.util.UUID


interface AppointmentsRepository {
    suspend fun getAppointments(doctorId: UUID): List<AppointmentResponse>
    suspend fun getUpcomingAppointments(doctorId: UUID): List<AppointmentResponse>
}