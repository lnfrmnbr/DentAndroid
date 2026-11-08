package com.example.dentapp.domain.usecase.calendar

import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import java.util.UUID
import javax.inject.Inject
import com.example.dentapp.di.RemoteDataSource

class GetArchiveAppointmentsUseCase @Inject constructor(
    @RemoteDataSource private val repository: AppointmentsRepository
) {
    suspend operator fun invoke(userId: UUID): List<Appointment> {
        return repository.getArchiveAppointments(userId)
    }
}