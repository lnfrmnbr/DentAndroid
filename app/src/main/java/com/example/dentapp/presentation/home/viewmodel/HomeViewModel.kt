package com.example.dentapp.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.data.remote.api.ApiService
import com.example.dentapp.di.SignUpUseCase
import com.example.dentapp.domain.repository.doctor.DoctorRepository
import com.example.dentapp.domain.usecase.auth.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
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