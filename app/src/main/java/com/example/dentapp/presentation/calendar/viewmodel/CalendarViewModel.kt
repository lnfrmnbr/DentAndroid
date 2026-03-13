package com.example.dentapp.presentation.calendar.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.repository.doctor.DoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    private val _daySelected = MutableStateFlow<LocalDate>(LocalDate.now())
    val daySelected: StateFlow<LocalDate> = _daySelected.asStateFlow()

    init {
    }

    fun selectDay(date: LocalDate) {
        _daySelected.value = date
    }

}