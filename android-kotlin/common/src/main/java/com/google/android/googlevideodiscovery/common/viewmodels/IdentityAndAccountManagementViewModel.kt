package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.fakes.FakeProfileNames
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdentityAndAccountManagementViewModel @Inject constructor(
    private val identityAndAccountManagementService: IdentityAndAccountManagementService,
) : ViewModel() {
    val account: Flow<Account?> = identityAndAccountManagementService.getLoggedInUser()

    private val _activeProfile = MutableStateFlow<AccountProfile?>(null)
    val activeProfile = _activeProfile.asStateFlow()

    fun performRegistration(afterRegistration: () -> Unit) {
        viewModelScope.launch {
            val response = identityAndAccountManagementService.register(
                username = "...",
                password = "..."
            )
            if (response.isSuccess) {
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
                afterLoginSuccess()
            }
        }
    }

    fun createNewProfile() {
        viewModelScope.launch {
            account.collectLatest { latestAccount ->
                val newProfileName = latestAccount?.getNewProfileName() ?: return@collectLatest
                identityAndAccountManagementService.createProfile(latestAccount, newProfileName)
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