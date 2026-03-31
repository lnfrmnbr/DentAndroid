package com.example.dentapp.presentation.appointment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.data.local.AuthDataStore
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.domain.usecase.appointment.AddAppointmentUseCase
import com.example.dentapp.domain.usecase.patients.AddPatientUseCase
import com.example.dentapp.domain.usecase.patients.GetAllPatientsUseCase
import com.example.dentapp.domain.usecase.patients.constants.PatientsErrors
import com.example.dentapp.utils.DateUtils
import com.example.dentapp.utils.GenderUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val addPatientUseCase: AddPatientUseCase,
    private val addAppointmentUseCase: AddAppointmentUseCase,
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val authDataStore: AuthDataStore
) : ViewModel() {
    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate

    private val _sex = MutableStateFlow("")
    val sex: StateFlow<String> = _sex

    private val _fio = MutableStateFlow("")
    val fio: StateFlow<String> = _fio

    private val _isFirstAppointment = MutableStateFlow(true)
    val isFirstAppointment: StateFlow<Boolean> = _isFirstAppointment

    private val _complaints = MutableStateFlow("")
    val complaints: StateFlow<String> = _complaints

    private val _comments = MutableStateFlow("")
    val comments: StateFlow<String> = _comments

    private val _patientId = MutableStateFlow<UUID?>(null)
    val patientId: StateFlow<UUID?> = _patientId

    private val _patients = MutableStateFlow<List<Patient>>(emptyList())
    val patients: StateFlow<List<Patient>> = _patients.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadPatients()
    }

    fun updateIsFirstAppointment(value: Boolean) {
        _isFirstAppointment.value = value
        clearError()
    }

    fun updateBirthDate(value: String) {
        _birthDate.value = value
        clearError()
    }

    fun updateEmail(value: String) {
        _email.value = value
        clearError()
    }

    fun updatePhoneNumber(value: String) {
        _phoneNumber.value = value
        clearError()
    }

    fun updateComments(value: String) {
        _comments.value = value
        clearError()
    }

    fun updateComplaints(value: String) {
        _complaints.value = value
        clearError()
    }

    fun updateSex(value: String) {
        _sex.value = value
        clearError()
    }

    fun updateFio(value: String) {
        _fio.value = value
        clearError()
    }

    private fun clearError() {
        _errorMessage.value = null
    }

    fun startAppointment(onSuccess: () -> Unit) {
        if (fio.value.length > 100) {
            _errorMessage.value = PatientsErrors.FULL_NAME_TOO_LONG
            return
        }

        if (fio.value.isEmpty() || sex.value.isEmpty() || birthDate.value.isEmpty()) {
            _errorMessage.value = PatientsErrors.FIELDS_REQUIRED
            return
        }

        if (email.value.isBlank() && phoneNumber.value.isBlank()) {
            _errorMessage.value = PatientsErrors.CONTACT_INFO_REQUIRED
            return
        }

        val genderCode = GenderUtils.toBdCode(sex.value)
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val patientId = if (patientId.value == null) {
                    addPatientUseCase(
                        fullName = fio.value,
                        birthDate = formatter.parse(birthDate.value)!!,
                        sex = genderCode,
                        email = email.value,
                        phoneNumber = phoneNumber.value
                    ).id
                } else patientId.value

                val doctorId = UUID.fromString(authDataStore.getDoctorId())

                if (patientId != null) {
                    addAppointmentUseCase(
                        doctorId = doctorId,
                        patientId = patientId,
                        date = LocalDateTime.now()
                    )
                    _errorMessage.value = null
                    onSuccess()
                } else {
                    _errorMessage.value = PatientsErrors.NOT_SAVED
                }

                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка начала приема: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun loadPatients() {
        viewModelScope.launch {
            _patients.value = getAllPatientsUseCase()
        }
    }

    fun selectPatient(patient: Patient) {
        _patientId.value = patient.id
        updateFio(patient.fullName)
        patient.phoneNumber?.let { updatePhoneNumber(it) }
        patient.email?.let { updateEmail(it) }
        updateBirthDate(DateUtils.toDDMMYYYY(patient.birthDate))
        updateSex(patient.sex)
    }

    fun clearSelectedPatient() {
        _patientId.value = null
        updateBirthDate("")
        updateSex("")
        updateEmail("")
        updatePhoneNumber("")
    }
}