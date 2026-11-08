package com.example.dentapp.domain.usecase.calendar

import com.example.dentapp.di.LocalDataSource
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import java.util.UUID
import javax.inject.Inject
import com.example.dentapp.di.RemoteDataSource

class GetAppointmentsUseCase @Inject constructor(
    @RemoteDataSource private val remoteRepository: AppointmentsRepository,
    @LocalDataSource private val localRepository: AppointmentsRepository
) {
    suspend operator fun invoke(userId: UUID? = null, isLocal: Boolean): List<Appointment> {
        return if (!isLocal) remoteRepository.getAppointments(userId)
        else localRepository.getAppointments()
    }
}
