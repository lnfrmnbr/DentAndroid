package com.example.dentapp.presentation.calendar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.domain.repository.doctor.DoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository
) : ViewModel() {
    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    init {
        updateFirstName()
    }

    private fun updateFirstName() {
        viewModelScope.launch {
            val firstName = doctorRepository.getFirstName()
            if (firstName != null) {
                _firstName.value = firstName
            }
        }
    }
}