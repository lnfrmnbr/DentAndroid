package com.example.dentapp.domain.usecase.appointment

import com.example.dentapp.di.LocalDataSource
import com.example.dentapp.di.RemoteDataSource
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import com.example.dentapp.presentation.periochart.utils.Tooth
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class AddAppointmentUseCase @Inject constructor(
    @RemoteDataSource private val remoteRepository: AppointmentsRepository,
    @LocalDataSource private val localRepository: AppointmentsRepository
) {
    suspend operator fun invoke(
        doctorId: UUID?,
        patientId: UUID,
        date: LocalDateTime,
        tags: List<String>? = null,
        diagnosis: List<String>? = null,
        complaints: String,
        comments: String,
        plan: String,
        isFirst: Boolean,
        indexes: Indexes,
        chart: List<Tooth>?,
        isLocal: Boolean
    ): Appointment? {
        return if (!isLocal) {
            remoteRepository.addAppointment(
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
            )
        } else {
            localRepository.addAppointment(
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
            )
        }
    }
}