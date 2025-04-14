package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.fakes.FakeProfileNames
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdentityAndAccountManagementViewModel @Inject constructor(
    private val identityAndAccountManagementService: IdentityAndAccountManagementService,
) : ViewModel() {
    private val _account = MutableStateFlow<Account?>(null)
    val account = _account.asStateFlow()

    private val _activeProfile = MutableStateFlow<AccountProfile?>(null)
    val activeProfile = _activeProfile.asStateFlow()

    fun performRegistration(afterRegistration: () -> Unit) {
        viewModelScope.launch {
            val response = identityAndAccountManagementService.register(
                username = "...",
                password = "..."
            )
            if (response.isSuccess) {
                val account = response.getOrThrow()
                _account.value = account
                afterRegistration()
            }
        }
    }

    fun performLogin(afterLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            val response = identityAndAccountManagementService.login(
                username = "...",
                password = "..."
            )
            if (response.isSuccess) {
                val account = response.getOrThrow()
                _account.value = account
                afterLoginSuccess()
            }
        }
    }

    fun createNewProfile(afterProfileCreation: () -> Unit) {
        viewModelScope.launch {
            val account = _account.value ?: return@launch
            val newProfileName = account.getNewProfileName() ?: return@launch
            val response =
                identityAndAccountManagementService.createProfile(account, newProfileName)
            if (response.isSuccess) {
                val result = response.getOrThrow()
                _account.value = result.account
                afterProfileCreation()
            }
        }
    }

    fun selectProfile(profile: AccountProfile, afterProfileSelection: () -> Unit) {
        _activeProfile.value = profile
        afterProfileSelection()
    }

    private fun Account.getNewProfileName(): String? {
        val existingNames = profiles.map { it.name }
        return when {
            FakeProfileNames.AANYA !in existingNames -> FakeProfileNames.AANYA
            FakeProfileNames.DIVYA !in existingNames -> FakeProfileNames.DIVYA
            FakeProfileNames.GUEST !in existingNames -> FakeProfileNames.GUEST
            else -> null
        }
    }
}