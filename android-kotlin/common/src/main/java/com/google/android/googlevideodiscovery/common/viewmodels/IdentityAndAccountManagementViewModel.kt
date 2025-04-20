package com.google.android.googlevideodiscovery.common.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.fakes.FakeProfileNames
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.services.EngageInteractionService
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import com.google.android.googlevideodiscovery.common.services.PublishContinueWatchingReason
import com.google.android.googlevideodiscovery.common.services.SyncAcrossDevicesConsentService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdentityAndAccountManagementViewModel @Inject constructor(
    private val identityAndAccountManagementService: IdentityAndAccountManagementService,
    private val engageInteractionService: EngageInteractionService,
    private val syncAcrossDevicesConsentService: SyncAcrossDevicesConsentService,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val loggedInAccount = identityAndAccountManagementService.loggedInAccount
    val activeProfile = identityAndAccountManagementService.activeProfile

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
            loggedInAccount.collectLatest { latestAccount ->
                val newProfileName = latestAccount?.getNewProfileName() ?: return@collectLatest
                identityAndAccountManagementService.createProfile(latestAccount, newProfileName)
            }
        }
    }

    fun selectProfile(profile: AccountProfile, afterProfileSelection: () -> Unit) {
        identityAndAccountManagementService.setActiveProfile(profile)
        afterProfileSelection()
        engageInteractionService.publishContinuationCluster(
            context = context,
            profileId = profile.id,
            reason = PublishContinueWatchingReason.PROFILE_SELECTION
        )
    }

    fun performLogout() {
        viewModelScope.launch {
            identityAndAccountManagementService.logoutAccounts()
        }
    }

    fun deleteCurrentProfile() {
        val currentProfile = activeProfile.value ?: return
        viewModelScope.launch {
            identityAndAccountManagementService.deleteProfile(currentProfile)
        }
    }

    fun updateSyncAcrossDevicesConsentValue(newConsentValue: Boolean) {
        val accountId = activeProfile.value?.account?.id ?: return
        viewModelScope.launch {
            syncAcrossDevicesConsentService.updateSyncAcrossDevicesConsentValue(
                accountId = accountId,
                newConsentValue = newConsentValue,
            )
        }
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