package com.google.android.googlevideodiscovery.common.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.engage.converters.DeleteReason
import com.google.android.googlevideodiscovery.common.engage.converters.PublishContinuationClusterReason
import com.google.android.googlevideodiscovery.common.engage.workers.DeleteClustersWorker.Companion.deleteClustersForEntireAccount
import com.google.android.googlevideodiscovery.common.engage.workers.DeleteClustersWorker.Companion.deleteClustersForProfile
import com.google.android.googlevideodiscovery.common.engage.workers.PublishContinuationClusterWorker.Companion.publishContinuationCluster
import com.google.android.googlevideodiscovery.common.fakes.FakeProfileNames
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import com.google.android.googlevideodiscovery.common.services.SyncAcrossDevicesConsentService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IdentityAndAccountManagementViewModel @Inject constructor(
    private val identityAndAccountManagementService: IdentityAndAccountManagementService,
    private val syncAcrossDevicesConsentService: SyncAcrossDevicesConsentService,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val loggedInAccount = identityAndAccountManagementService.loggedInAccount.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        null
    )
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
            val latestAccount = loggedInAccount.value
            val newProfileName = latestAccount?.getNewProfileName() ?: return@launch
            identityAndAccountManagementService.createProfile(latestAccount, newProfileName)
        }
    }

    fun selectProfile(profile: AccountProfile, afterProfileSelection: () -> Unit) {
        identityAndAccountManagementService.setActiveProfile(profile)
        afterProfileSelection()
        context.publishContinuationCluster(
            profileId = profile.id,
            reason = PublishContinuationClusterReason.PROFILE_SELECTION
        )
    }

    fun performLogout() {
        val accountId = loggedInAccount.value?.id ?: return
        viewModelScope.launch {
            identityAndAccountManagementService.logoutAccounts()
            context.deleteClustersForEntireAccount(
                accountId = accountId,
                reason = DeleteReason.USER_LOGOUT
            )
        }
    }

    fun deleteCurrentProfile() {
        val currentProfile = activeProfile.value ?: return
        viewModelScope.launch {
            identityAndAccountManagementService.deleteProfile(currentProfile)
            context.deleteClustersForProfile(
                profileId = currentProfile.id,
                reason = DeleteReason.ACCOUNT_PROFILE_DELETION
            )
        }
    }

    fun deleteCurrentAccount() {
        val currentAccount = loggedInAccount.value ?: return
        viewModelScope.launch {
            identityAndAccountManagementService.deleteAccount(currentAccount)
            context.deleteClustersForEntireAccount(
                accountId = currentAccount.id,
                reason = DeleteReason.ACCOUNT_DELETION
            )
        }
    }

    fun updateSyncAcrossDevicesConsentValue(newConsentValue: Boolean) {
        val accountId = loggedInAccount.value?.id ?: return
        viewModelScope.launch {
            syncAcrossDevicesConsentService.updateSyncAcrossDevicesConsentValue(
                accountId = accountId,
                newConsentValue = newConsentValue,
            )
            if (!newConsentValue) {
                context.deleteClustersForEntireAccount(
                    accountId = accountId,
                    reason = DeleteReason.LOSS_OF_CONSENT
                )
            }
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