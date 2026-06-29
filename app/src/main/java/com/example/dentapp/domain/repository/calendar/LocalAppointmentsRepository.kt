package com.example.dentapp.domain.repository.calendar

import com.example.dentapp.data.local.entity.AppointmentEntity
import java.util.UUID

interface LocalAppointmentsRepository {
    suspend fun getAllAppointments(): List<AppointmentEntity>
    suspend fun saveAppointment(appointment: AppointmentEntity)
    suspend fun getAppointmentById(id: UUID): AppointmentEntity?
}