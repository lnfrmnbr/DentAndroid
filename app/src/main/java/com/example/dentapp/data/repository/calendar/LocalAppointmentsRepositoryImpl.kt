package com.example.dentapp.data.repository.calendar

import com.example.dentapp.data.local.dao.AppointmentDao
import com.example.dentapp.data.local.entity.AppointmentEntity
import com.example.dentapp.data.local.entity.toDomain
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import com.example.dentapp.presentation.periochart.utils.Tooth
import com.google.gson.Gson
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class LocalAppointmentsRepositoryImpl @Inject constructor(
    private val appointmentDao: AppointmentDao
) : AppointmentsRepository {

    override suspend fun getAppointments(doctorId: UUID?): List<Appointment> {
        return appointmentDao.getAllAppointments().map { it.toDomain() }
    }

    override suspend fun getUpcomingAppointments(doctorId: UUID?): List<Appointment> {
        TODO("Not yet implemented")
    }

    override suspend fun getArchiveAppointments(doctorId: UUID?): List<Appointment> {
        TODO("Not yet implemented")
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
    ): Appointment? {
        val gson = Gson()
        val appointment = AppointmentEntity(
            patientId = patientId,
            date = System.currentTimeMillis(),
            tags = gson.toJson(tags),
            diagnosis = gson.toJson(diagnosis),
            complaints = complaints,
            comments = comments,
            plan = plan,
            isFirst = isFirst,
            indexes = gson.toJson(indexes),
            chart = gson.toJson(chart)
        )
        appointmentDao.insertAppointment(appointment)
        return getAppointmentById(appointment.id)?.toDomain()
    }

    suspend fun getAppointmentById(id: UUID): AppointmentEntity? {
        return appointmentDao.getAppointmentById(id)
    }
}