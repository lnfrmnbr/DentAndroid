package com.example.dentapp.domain.usecase.appointment

import com.example.dentapp.data.local.entity.toDomain
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.repository.calendar.LocalAppointmentsRepository
import javax.inject.Inject

class GetLocalAppointmentsUseCase @Inject constructor(
    private val repository: LocalAppointmentsRepository
) {
    suspend operator fun invoke(): List<Appointment> {
        return repository.getAllAppointments().map {
            it.toDomain()
        }
    }
}