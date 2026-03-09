package com.example.dentapp.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.di.SignUpUseCase
import com.example.dentapp.domain.usecase.auth.ValidatePasswordUseCase
import com.example.dentapp.domain.usecase.auth.constants.AuthErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName

    private val _patronymic = MutableStateFlow("")
    val patronymic: StateFlow<String> = _patronymic

    private val _sex = MutableStateFlow("")
    val sex: StateFlow<String> = _sex

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate

    private val _position = MutableStateFlow("")
    val position: StateFlow<String> = _position

    fun updateEmail(value: String) {
        _email.value = value
        clearError()
    }

    fun updatePosition(value: String) {
        _position.value = value
        clearError()
    }

    fun updateBirthDate(value: String) {
        _birthDate.value = value
        clearError()
    }

    fun updateFirstName(value: String) {
        _firstName.value = value
        clearError()
    }

    fun updateLastName(value: String) {
        _lastName.value = value
        clearError()
    }

    fun updatePatronymic(value: String) {
        _patronymic.value = value
        clearError()
    }

    fun updateSex(value: String) {
        _sex.value = value
        clearError()
    }

    fun updatePassword(value: String) {
        _password.value = value
        clearError()
    }

    fun updateConfirmPassword(value: String) {
        _confirmPassword.value = value
        clearError()
    }

    fun signUp(onSuccess: () -> Unit) {
        if (lastName.value.length > 50) {
            _errorMessage.value = AuthErrors.LASTNAME_TOO_LONG
            return
        }

        if (firstName.value.length > 20) {
            _errorMessage.value = AuthErrors.FIRSTNAME_TOO_LONG
            return
        }

        if (patronymic.value.length > 20) {
            _errorMessage.value = AuthErrors.PATRONYMIC_TOO_LONG
            return
        }

        val passwordValidation = validatePasswordUseCase(password.value, confirmPassword.value)
        if (!passwordValidation.successful) {
            _errorMessage.value = passwordValidation.errorMessage
            return
        }

        if (email.value.isEmpty() || lastName.value.isEmpty() || firstName.value.isEmpty() || sex.value.isEmpty()) {
            _errorMessage.value = AuthErrors.FIELDS_REQUIRED
            return
        }

        val genderCode = when (sex.value) {
            "M", "М" -> "M"
            else -> "F"
        }

        _isLoading.value = true
        viewModelScope.launch {
            val patronymicString = if (patronymic.value.isEmpty()) "" else " " + patronymic.value
            val result = signUpUseCase(
                email = email.value,
                password = password.value,
                fullName = "${lastName.value} ${firstName.value}$patronymicString",
                firstName = firstName.value,
                sex = genderCode
            )
            _isLoading.value = false

            if (result.isSuccess) {
                clearError()
                onSuccess()
            } else {
                _errorMessage.value =
                    result.exceptionOrNull()?.message ?: AuthErrors.REGISTRATION_FAILED
            }
        }
    }

    private fun clearError() {
        _errorMessage.value = null
    }
}