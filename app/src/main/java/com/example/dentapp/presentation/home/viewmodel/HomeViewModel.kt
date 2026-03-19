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
    private val _firstName = MutableStateFlow("Имя")
    val firstName: StateFlow<String> = _firstName

    private val _patronymic = MutableStateFlow("Отчество")
    val patronymic: StateFlow<String> = _patronymic

    private val _selectedTab = MutableStateFlow(HomeTabs.UpcomingAppointments.title)
    val selectedTab: StateFlow<String> = _selectedTab

    init {
        updateProfileInfo()
    }

    private fun updateProfileInfo() {
        viewModelScope.launch {
           val firstName = authDataStore.getFirstName()
            val patronymic = authDataStore.getPatronymic()
            if (firstName != null) {
                _firstName.value = firstName
                if (patronymic.isNullOrBlank()) {
                    _patronymic.value = ""
                }
            }
            if (patronymic != null) {
                _patronymic.value = patronymic
            }
        }
    }

    fun selectTab(selectedTab: String) {
        _selectedTab.value = selectedTab
    }

    enum class HomeTabs(val title: String) {
        UpcomingAppointments("Ближайшие\nприемы"),
        ArchiveAppointments("Архив\nзаписей"),
        ListOfPatients("Список\nпациентов")
    }
}