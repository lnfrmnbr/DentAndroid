package com.example.dentapp.presentation.calendar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.data.local.AuthDataStore
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.usecase.calendar.GetAppointmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAppointmentsUseCase: GetAppointmentsUseCase,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> = _appointments.asStateFlow()

    private val _daySelected = MutableStateFlow<LocalDate>(LocalDate.now())
    val daySelected: StateFlow<LocalDate> = _daySelected.asStateFlow()

    private val _appointmentsForDay = MutableStateFlow<List<Appointment>>(emptyList())
    val appointmentsForDay: StateFlow<List<Appointment>> = _appointmentsForDay.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadAppointments()
    }

    fun selectDay(date: LocalDate) {
        _daySelected.value = date
        _appointmentsForDay.value = _appointments.value.filter { it.date.toLocalDate() == date }.sortedBy { it.date }
    }

    fun loadAppointments() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userId = authDataStore.getUserId()

                _appointments.value = getAppointmentsUseCase(UUID.fromString(userId))
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки приемов: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

}