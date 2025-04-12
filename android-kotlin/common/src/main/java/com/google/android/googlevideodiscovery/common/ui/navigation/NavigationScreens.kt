package com.google.android.googlevideodiscovery.common.ui.navigation

import androidx.compose.runtime.Composable
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.Movie
import com.google.android.googlevideodiscovery.common.models.TvEpisode
import kotlinx.serialization.Serializable

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
    )

    @Composable
    fun HomeScreen(activeProfile: AccountProfile, movies: List<Movie>, tvEpisodes: List<TvEpisode>)

    @Composable
    fun SettingsScreen()

    @Composable
    fun EntityScreen()
}

@Serializable
internal object LoginScreen

@Serializable
internal object ProfilesScreen

@Serializable
internal object HomeScreen

@Serializable
internal object SettingsScreen

@Serializable
data class EntityScreen(val entityId: String)
