package com.google.android.googlevideodiscovery.common.ui.navigation

import androidx.compose.runtime.Composable
import com.google.android.googlevideodiscovery.common.engage.converters.PublishContinuationClusterReason
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.MovieEntity
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.models.TvEpisodeEntity
import kotlinx.serialization.Serializable
import kotlin.time.Duration

interface NavigationScreens {
    @Composable
    fun LoginScreen(
        performRegistration: () -> Unit,
        performLogin: () -> Unit
    )

    @Composable
    fun ProfilesScreen(
        account: Account,
        onCreateProfile: () -> Unit,
        onSelectProfile: (AccountProfile) -> Unit,
        deleteCurrentAccount: () -> Unit
    )

    @Composable
    fun HomeScreen(
        loggedInAccount: Account,
        activeProfile: AccountProfile,
        continueWatchingEntities: List<ContinueWatchingEntity>,
        movieEntities: List<MovieEntity>,
        tvEpisodeEntities: List<TvEpisodeEntity>,
        onConfirmRemoveFromContinueWatchingRow: (ContinueWatchingEntity) -> Unit,
        openProfileSelectionPage: () -> Unit,
        deleteCurrentProfile: () -> Unit,
        logout: () -> Unit,
        updateUserConsentToShareDataWithGoogle: (consentValue: Boolean) -> Unit,
        onEntityClick: (String) -> Unit,
    )

    @Composable
    fun SettingsScreen()

    @Composable
    fun EntityScreen(
        entity: PlaybackEntity?,
        isPlaying: Boolean,
        updateIsPlaying: (Boolean) -> Unit,
        onUpdatePlaybackPosition: (Duration, PublishContinuationClusterReason?) -> Unit
    )
}

@Serializable
internal data class DeeplinkHandlerScreen(
    val entityId: String,
    val profileId: String?
)

@Serializable
internal object LoginScreen

@Serializable
internal object ProfilesScreen

@Serializable
internal object HomeScreen

@Serializable
internal object SettingsScreen

@Serializable
internal data class EntityScreen(val entityId: String)
