package com.example.dentapp.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.data.local.AuthDataStore
import com.example.dentapp.domain.repository.doctor.DoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val doctorRepository: DoctorRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {
    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    init {
        updateFirstName()
    }

    private fun updateFirstName() {
        viewModelScope.launch {
           val firstName = authDataStore.getDoctorId()
            if (firstName != null) {
                _firstName.value = firstName
            }
        }
    }
}