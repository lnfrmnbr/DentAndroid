package com.example.dentapp.domain.repository.calendar

import com.example.dentapp.data.remote.model.response.calendar.AppointmentResponse


interface AppointmentsRepository {
    suspend fun getAppointments(): List<AppointmentResponse>
}