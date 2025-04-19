package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.services.ContinueWatchingService
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContinueWatchingViewModel @Inject constructor(
    identityAndAccountManagementService: IdentityAndAccountManagementService,
    private val continueWatchingService: ContinueWatchingService,
) : ViewModel() {
    private val _activeProfile = identityAndAccountManagementService.activeProfile

    private val _continueWatchingEntities = continueWatchingService.getAll()
    val continueWatchingEntities = combine(
        _activeProfile,
        _continueWatchingEntities
    ) { activeProfile, continueWatchingEntities ->
        continueWatchingEntities.filter { it.profileId == activeProfile?.id }
    }

    fun removeFromContinueWatching(continueWatchingEntity: ContinueWatchingEntity) {
        viewModelScope.launch {
            continueWatchingService.removeFromContinueWatching(continueWatchingEntity)
        }
    }
}