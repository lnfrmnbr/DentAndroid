package com.example.dentapp.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dentapp.domain.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn.asStateFlow()

    private val _isOfflineMode = MutableStateFlow<Boolean?>(null)
    val isOfflineMode: StateFlow<Boolean?> = _isOfflineMode.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        checkAuthStatus()
        checkOfflineStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            _isLoading.value = true
            val loggedIn = authRepository.isUserLoggedIn()
            _isLoggedIn.value = loggedIn
            _isLoading.value = false
        }
    }

    private fun checkOfflineStatus() {
        viewModelScope.launch {
            _isLoading.value = true
            val idOfflineMode = authRepository.isOfflineMode()
            _isOfflineMode.value = idOfflineMode
            _isLoading.value = false
        }
    }

    fun setLoggedIn(loggedIn: Boolean) {
        _isLoggedIn.value = loggedIn
    }

    fun logout() {
        _isLoggedIn.value = false
    }
}