package com.example.dentapp.domain.usecase.calendar

import com.example.dentapp.data.remote.model.response.calendar.toDomain
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import javax.inject.Inject

class GetAppointmentsUseCase @Inject constructor(
    private val repository: AppointmentsRepository
) {
    suspend operator fun invoke(): List<Appointment> {
        return repository.getAppointments().map { it.toDomain() }
    }
}
