package com.example.dentapp.presentation.appointment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.R
import com.example.dentapp.domain.model.appointment.Appointment
import com.example.dentapp.domain.model.appointment.Indexes
import com.example.dentapp.domain.model.patients.Patient
import com.example.dentapp.domain.usecase.patients.GetAllPatientsUseCase
import com.example.dentapp.domain.usecase.patients.constants.PatientsErrors
import com.example.dentapp.presentation.periochart.utils.Tooth
import com.example.dentapp.utils.DateUtils
import com.example.dentapp.utils.DateUtils.toDDMMYYYY
import com.example.dentapp.utils.GenderUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(
    private val getAllPatientsUseCase: GetAllPatientsUseCase
) : ViewModel() {
    val teethUnit = listOf(
        Tooth(18, R.raw.t18),
        Tooth(17, R.raw.t17),
        Tooth(16, R.raw.t16),
        Tooth(15, R.raw.t15),
        Tooth(14, R.raw.t14),
        Tooth(13, R.raw.t13),
        Tooth(12, R.raw.t12),
        Tooth(11, R.raw.t11),
        Tooth(21, R.raw.t21),
        Tooth(22, R.raw.t22),
        Tooth(23, R.raw.t23),
        Tooth(24, R.raw.t24),
        Tooth(25, R.raw.t25),
        Tooth(26, R.raw.t26),
        Tooth(27, R.raw.t27),
        Tooth(28, R.raw.t28),
        Tooth(48, R.raw.t48),
        Tooth(47, R.raw.t47),
        Tooth(46, R.raw.t46),
        Tooth(45, R.raw.t45),
        Tooth(44, R.raw.t44),
        Tooth(43, R.raw.t43),
        Tooth(42, R.raw.t42),
        Tooth(41, R.raw.t41),
        Tooth(31, R.raw.t31),
        Tooth(32, R.raw.t32),
        Tooth(33, R.raw.t33),
        Tooth(34, R.raw.t34),
        Tooth(35, R.raw.t35),
        Tooth(36, R.raw.t36),
        Tooth(37, R.raw.t37),
        Tooth(38, R.raw.t38)
    )

    private val _appointment = MutableStateFlow<Appointment?>(null)
    val appointment: StateFlow<Appointment?> = _appointment.asStateFlow()

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

    private val _diagnosis = MutableStateFlow<List<String>>(emptyList())
    val diagnosis: StateFlow<List<String>> = _diagnosis.asStateFlow()

    private val _indexes = MutableStateFlow(Indexes())
    val indexes: StateFlow<Indexes> = _indexes

    private val _treatmentPlan = MutableStateFlow("")
    val treatmentPlan: StateFlow<String> = _treatmentPlan.asStateFlow()

    private val _chart = MutableStateFlow(teethUnit.map { it.copy() })
    val chart: StateFlow<List<Tooth>> = _chart.asStateFlow()

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

    fun updateTreatmentPlan(value: String) {
        _treatmentPlan.value = value
    }

    private fun clearError() {
        _errorMessage.value = null
    }

    fun deleteDiagnose(diagnose: String) {
        _diagnosis.update { currentList ->
            currentList.filter { it != diagnose }
        }
    }

    fun addDiagnose(diagnose: String) {
        if (diagnose.isBlank()) return

        val trimmedDiagnose = diagnose.trim()

        if (!_diagnosis.value.contains(trimmedDiagnose)) {
            _diagnosis.update { currentList ->
                currentList + trimmedDiagnose
            }
        }
    }

    fun getAllDiseases(): List<String> {
        return listOf(
            "Кариес",
            "Пульпит",
            "Периодонтит",
            "Гингивит",
            "Стоматит",
            "Пародонтоз",
            "Пародонтит",
            "Флюороз",
            "Эрозия эмали",
            "Киста зуба",
            "Абсцесс",
            "Альвеолит"
        )
    }

    fun startAppointment(onSuccess: (Appointment) -> Unit) {
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

        val appointment = Appointment(
            complaints = complaints.value,
            comments = comments.value,
            patient = Patient(
                id = patientId.value,
                fullName = fio.value,
                birthDate = formatter.parse(birthDate.value)!!,
                sex = genderCode,
                email = email.value,
                phoneNumber = phoneNumber.value
            ),
            date = LocalDateTime.now(),
            isFirst = isFirstAppointment.value
        )

        onSuccess(appointment)

        _isLoading.value = false

        /*viewModelScope.launch {
            try {
                val doctorId = UUID.fromString(authDataStore.getDoctorId())

                val patientId = if (patientId.value == null) {
                    addPatientUseCase(
                        fullName = fio.value,
                        birthDate = formatter.parse(birthDate.value)!!,
                        sex = genderCode,
                        email = email.value,
                        phoneNumber = phoneNumber.value
                    ).id
                } else patientId.value

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
        }*/
    }

    fun setAppointment(appointment: Appointment?) {
        _appointment.value = appointment
        appointment?.comments?.let {
            _comments.value = it
        }
        appointment?.complaints?.let {
            _complaints.value = it
        }
        appointment?.patient?.let { patient ->
            _fio.value = patient.fullName
            _birthDate.value = toDDMMYYYY(patient.birthDate)
            _sex.value = patient.sex
        }
        appointment?.patient?.phoneNumber?.let {
            _phoneNumber.value = it
        }
        appointment?.patient?.email?.let {
            _email.value = it
        }
        appointment?.isFirst?.let {
            _isFirstAppointment.value = it
        }
        appointment?.diagnosis?.let {
            _diagnosis.value = it
        }
        appointment?.indexes?.let {
            _indexes.value = it
        }
        appointment?.plan?.let {
            _treatmentPlan.value = it
        }
        appointment?.chart?.let {
            _chart.value = it
        }
    }

    private fun loadPatients() {
        viewModelScope.launch {
            try {
                _patients.value = getAllPatientsUseCase()
            } catch (e: Exception) {
                _errorMessage.value = "Ошибка загрузки списка пациентов: ${e.message}"
            } finally {
                _isLoading.value = false
            }
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