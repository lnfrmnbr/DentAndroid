package com.example.dentapp.domain.usecase.appointment

import com.example.dentapp.data.remote.model.response.calendar.toDomain
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.repository.calendar.AppointmentsRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class AddAppointmentUseCase @Inject constructor(
    private val appointmentsRepository: AppointmentsRepository
) {
    suspend operator fun invoke(
        doctorId: UUID,
        patientId: UUID,
        date: LocalDateTime,
        tags: List<String>? = null,
        bop: Double? = null,
        russel: Double? = null,
        api: Double? = null,
        chart: String? = null,
        fileUrl: String? = null
    ): Appointment {
        return appointmentsRepository.addAppointment(
            doctorId = doctorId,
            patientId = patientId,
            date = date,
            tags = tags,
            bop = bop,
            russel = russel,
            api = api,
            chart = chart,
            fileUrl = fileUrl
        ).toDomain()
    }
}