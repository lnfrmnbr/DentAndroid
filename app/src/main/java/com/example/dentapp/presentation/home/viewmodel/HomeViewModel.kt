package com.example.dentapp.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.data.local.AuthDataStore
import com.example.dentapp.domain.model.calendar.Appointment
import com.example.dentapp.domain.usecase.calendar.GetArchiveAppointmentsUseCase
import com.example.dentapp.domain.usecase.calendar.GetUpcomingAppointmentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authDataStore: AuthDataStore,
    private val getUpcomingAppointmentsUseCase: GetUpcomingAppointmentsUseCase,
    private val getArchiveAppointmentsUseCase: GetArchiveAppointmentsUseCase
    ) : ViewModel() {
    private val _firstName = MutableStateFlow("Имя")
    val firstName: StateFlow<String> = _firstName

    private val _patronymic = MutableStateFlow("Отчество")
    val patronymic: StateFlow<String> = _patronymic

    private val _selectedTab = MutableStateFlow(HomeTabs.UpcomingAppointments.title)
    val selectedTab: StateFlow<String> = _selectedTab

    private val _upcomingAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val upcomingAppointments: StateFlow<List<Appointment>> = _upcomingAppointments.asStateFlow()

    private val _archiveAppointments = MutableStateFlow<List<Appointment>>(emptyList())
    val archiveAppointments: StateFlow<List<Appointment>> = _archiveAppointments.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {

                loadProfileInfo()
                loadUpcomingAppointments()
                loadArchiveAppointments()

            } catch (e: Exception) {
                _error.value = "Ошибка загрузки данных: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadProfileInfo() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
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
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки профиля: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadUpcomingAppointments() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val doctorId = authDataStore.getDoctorId()
                val loadedAppointments = getUpcomingAppointmentsUseCase(UUID.fromString(doctorId))
                _upcomingAppointments.value = loadedAppointments
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки ближайших приемов: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadArchiveAppointments() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val doctorId = authDataStore.getDoctorId()
                val loadedAppointments = getArchiveAppointmentsUseCase(UUID.fromString(doctorId))
                _archiveAppointments.value = loadedAppointments
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки ближайших приемов: ${e.message}"
            } finally {
                _isLoading.value = false
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