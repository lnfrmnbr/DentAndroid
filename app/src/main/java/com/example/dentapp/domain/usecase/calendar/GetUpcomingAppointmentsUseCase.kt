package com.example.dentapp.domain.usecase.calendar

import com.example.dentapp.di.RemoteDataSource
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import java.util.UUID
import javax.inject.Inject

class GetUpcomingAppointmentsUseCase @Inject constructor(
    @RemoteDataSource private val repository: AppointmentsRepository
) {
    suspend operator fun invoke(userId: UUID): List<Appointment> {
        return repository.getUpcomingAppointments(userId)
    }
}
