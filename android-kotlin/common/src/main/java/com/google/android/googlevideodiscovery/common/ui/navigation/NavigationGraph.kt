package com.google.android.googlevideodiscovery.common.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.android.googlevideodiscovery.common.ui.foundation.Foundations
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalFoundations
import com.google.android.googlevideodiscovery.common.viewmodels.IdentityAndAccountManagementViewModel
import com.google.android.googlevideodiscovery.common.viewmodels.MediaContentViewModel
import com.google.android.googlevideodiscovery.common.viewmodels.PlaybackEntityViewModel
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun NavigationGraph(
    foundations: Foundations,
    modifier: Modifier = Modifier,
    screens: NavigationScreens = remember { NavigationScreensImpl() },
    iamViewModel: IdentityAndAccountManagementViewModel = hiltViewModel(),
    mediaContentViewModel: MediaContentViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalFoundations provides foundations) {
        NavHost(
            navController = navController,
            modifier = modifier,
            startDestination = LoginScreen
        ) {
            composable<LoginScreen> {
                val postAuth = {
                    navController.navigate(ProfilesScreen) {
                        popUpTo(LoginScreen) {
                            inclusive = true
                        }
                    }
                }

                screens.LoginScreen(
                    performRegistration = {
                        iamViewModel.performRegistration {
                            postAuth()
                        }
                    },
                    performLogin = {
                        iamViewModel.performLogin {
                            postAuth()
                        }
                    }
                )
            }
            composable<ProfilesScreen> {
                val accountState = iamViewModel.account.collectAsStateWithLifecycle()

                accountState.value?.let { account ->
                    screens.ProfilesScreen(
                        account = account,
                        onCreateProfile = {
                            iamViewModel.createNewProfile {}
                        },
                        onSelectProfile = { selectedProfile ->
                            iamViewModel.selectProfile(selectedProfile) {
                                navController.navigate(HomeScreen) {
                                    popUpTo(ProfilesScreen) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    )
                }
            }
            composable<HomeScreen> {
                val activeProfile = iamViewModel.activeProfile.collectAsStateWithLifecycle().value
                    ?: throw Error("No active profile selected")

                val movies = mediaContentViewModel.movies.collectAsStateWithLifecycle().value
                val tvEpisodes =
                    mediaContentViewModel.tvEpisodes.collectAsStateWithLifecycle().value

                screens.HomeScreen(
                    activeProfile = activeProfile,
                    movies = movies,
                    tvEpisodes = tvEpisodes,
                    onEntityClick = { playbackEntity ->
                        navController.navigate(
                            EntityScreen(
                                entityId = playbackEntity.entityId,
                                startFromMillis = playbackEntity.playbackPosition?.inWholeMilliseconds
                                    ?: 0
                            )
                        )
                    }
                )
            }
            composable<SettingsScreen> { screens.SettingsScreen() }
            composable<EntityScreen> {
                val entityScreen = it.toRoute<EntityScreen>()

                val playbackEntityViewModel = hiltViewModel<PlaybackEntityViewModel>()
                val playbackEntity =
                    playbackEntityViewModel.playbackEntity.collectAsStateWithLifecycle()
                val isPlaying = playbackEntityViewModel.isPlaying.collectAsStateWithLifecycle()

                LaunchedEffect(entityScreen.entityId, entityScreen.startFromMillis) {
                    playbackEntityViewModel.loadPlaybackEntity(
                        entityId = entityScreen.entityId,
                        initialPlaybackPosition = entityScreen.startFromMillis?.toDuration(
                            DurationUnit.MILLISECONDS
                        )
                    )
                }

                screens.EntityScreen(
                    entity = playbackEntity.value,
                    isPlaying = isPlaying.value,
                    updateIsPlaying = { newIsPlaying ->
                        playbackEntityViewModel.updateIsPlaying(
                            newIsPlaying
                        )
                    },
                    onUpdatePlaybackPosition = { newPosition, reason ->
                        playbackEntityViewModel.updatePlaybackPosition(
                            newPosition = newPosition,
                            reason = reason
                        )
                    })
            }
        }
    }
}