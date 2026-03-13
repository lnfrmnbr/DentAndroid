package com.example.dentapp.presentation.calendar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.usecase.calendar.GetAppointmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAppointmentsUseCase: GetAppointmentsUseCase,
    ) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    private val _daySelected = MutableStateFlow<LocalDate>(LocalDate.now())
    val daySelected: StateFlow<LocalDate> = _daySelected.asStateFlow()

    private val _appointmentsForDay = MutableStateFlow<List<Appointment>>(emptyList())
    val appointmentsForDay: StateFlow<List<Appointment>> = _appointmentsForDay.asStateFlow()

    init {
        loadAppointments()
    }

    fun selectDay(date: LocalDate) {
        _daySelected.value = date
        _appointmentsForDay.value = _appointments.value.filter { it.date.toLocalDate() == date }
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            //_isLoading.value = true
            try {
                _appointments.value = getAppointmentsUseCase()
                //_error.value = null
            } catch (e: Exception) {
                //_error.value = "Ошибка загрузки товаров: ${e.message}"
            } finally {
               // _isLoading.value = false
            }
        }
    }

}