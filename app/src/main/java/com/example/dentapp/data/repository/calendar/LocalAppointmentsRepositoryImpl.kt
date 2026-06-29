package com.example.dentapp.data.repository.calendar

import com.example.dentapp.data.local.dao.AppointmentDao
import com.example.dentapp.data.local.entity.AppointmentEntity
import com.example.dentapp.domain.repository.calendar.LocalAppointmentsRepository
import java.util.UUID
import javax.inject.Inject

class LocalAppointmentsRepositoryImpl @Inject constructor(
    private val appointmentDao: AppointmentDao
) : LocalAppointmentsRepository {

    override suspend fun getAllAppointments(): List<AppointmentEntity> {
        return appointmentDao.getAllAppointments()
    }

    override suspend fun saveAppointment(appointment: AppointmentEntity) {
        appointmentDao.insertAppointment(appointment)
    }

    override suspend fun getAppointmentById(id: UUID): AppointmentEntity? {
        return appointmentDao.getAppointmentById(id)
    }
}