package com.google.android.googlevideodiscovery.common.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.services.ContinueWatchingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContinueWatchingViewModel @Inject constructor(
    private val continueWatchingService: ContinueWatchingService,
) : ViewModel() {

    private val _continueWatchingEntities =
        MutableStateFlow<List<ContinueWatchingEntity>>(emptyList())
    val continueWatchingEntities = _continueWatchingEntities.asStateFlow()

    fun loadContinueWatchingEntities(profileId: String) {
        viewModelScope.launch {
            _continueWatchingEntities.value = continueWatchingService.getMany(profileId = profileId)
        }
    }
}