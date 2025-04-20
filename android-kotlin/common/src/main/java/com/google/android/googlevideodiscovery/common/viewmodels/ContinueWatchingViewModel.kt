package com.google.android.googlevideodiscovery.common.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.engage.converters.PublishContinuationClusterReason
import com.google.android.googlevideodiscovery.common.engage.workers.PublishContinuationClusterWorker.Companion.publishContinuationCluster
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.services.ContinueWatchingService
import com.google.android.googlevideodiscovery.common.services.IdentityAndAccountManagementService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContinueWatchingViewModel @Inject constructor(
    identityAndAccountManagementService: IdentityAndAccountManagementService,
    private val continueWatchingService: ContinueWatchingService,
    @ApplicationContext private val context: Context,
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
            context.publishContinuationCluster(
                profileId = continueWatchingEntity.profileId,
                reason = PublishContinuationClusterReason.ENTITY_REMOVED_FROM_CONTINUE_WATCHING_ROW,
            )
        }
    }
}