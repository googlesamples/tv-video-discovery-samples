package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.services.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authService: AuthService = AuthService()
) : ViewModel() {
    private val _account = MutableStateFlow<Account?>(null)
    val account: StateFlow<Account?> = _account

    fun performRegistration(afterRegistration: () -> Unit) {
        viewModelScope.launch {
            loginOrRegister(afterRegistration)
        }
    }

    fun performLogin(afterLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            loginOrRegister(afterLoginSuccess)
        }
    }

    private suspend fun loginOrRegister(postAuth: () -> Unit) {
        val response = authService.login(
            username = "...",
            password = "..."
        )
        if (response.isSuccess) {
            val account = response.getOrThrow()
            _account.value = account
            postAuth()
        }
    }
}