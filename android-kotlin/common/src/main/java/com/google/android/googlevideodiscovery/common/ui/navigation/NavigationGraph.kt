package com.google.android.googlevideodiscovery.common.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.android.googlevideodiscovery.common.ui.foundation.Foundations
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalFoundations
import com.google.android.googlevideodiscovery.common.ui.utils.LockScreenOrientation
import com.google.android.googlevideodiscovery.common.ui.utils.ScreenOrientation
import com.google.android.googlevideodiscovery.common.viewmodels.ContinueWatchingViewModel
import com.google.android.googlevideodiscovery.common.viewmodels.IdentityAndAccountManagementViewModel
import com.google.android.googlevideodiscovery.common.viewmodels.MediaContentViewModel
import com.google.android.googlevideodiscovery.common.viewmodels.PlaybackEntityViewModel

@Composable
fun NavigationGraph(
    foundations: Foundations,
    modifier: Modifier = Modifier,
    screens: NavigationScreens = remember { NavigationScreensImpl() },
) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalFoundations provides foundations) {
        NavHost(
            navController = navController,
            modifier = modifier,
            startDestination = LoginScreen
        ) {
            composable<LoginScreen> {
                val iamViewModel = hiltViewModel<IdentityAndAccountManagementViewModel>()

                val accountState = iamViewModel.loggedInAccount.collectAsStateWithLifecycle(null)
                val navigateToProfilesScreen = {
                    navController.navigate(ProfilesScreen) {
                        popUpTo(LoginScreen) {
                            inclusive = true
                        }
                    }
                }

                LaunchedEffect(accountState.value) {
                    if (accountState.value != null) {
                        navigateToProfilesScreen()
                    }
                }

                screens.LoginScreen(
                    performRegistration = {
                        iamViewModel.performRegistration {
                            navigateToProfilesScreen()
                        }
                    },
                    performLogin = {
                        iamViewModel.performLogin {
                            navigateToProfilesScreen()
                        }
                    }
                )
            }
            composable<ProfilesScreen> {
                val iamViewModel = hiltViewModel<IdentityAndAccountManagementViewModel>()

                val accountState = iamViewModel.loggedInAccount.collectAsStateWithLifecycle(null)

                accountState.value?.let { account ->
                    screens.ProfilesScreen(
                        account = account,
                        onCreateProfile = {
                            iamViewModel.createNewProfile()
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
                val iamViewModel = hiltViewModel<IdentityAndAccountManagementViewModel>()
                val mediaContentViewModel = hiltViewModel<MediaContentViewModel>()
                val continueWatchingViewModel = hiltViewModel<ContinueWatchingViewModel>()

                val activeProfile = iamViewModel.activeProfile.collectAsStateWithLifecycle().value
                    ?: throw Error("No active profile selected")

                val continueWatchingEntities =
                    continueWatchingViewModel.continueWatchingEntities.collectAsStateWithLifecycle(
                        emptyList()
                    ).value
                val movies = mediaContentViewModel.movies.collectAsStateWithLifecycle().value
                val tvEpisodes =
                    mediaContentViewModel.tvEpisodes.collectAsStateWithLifecycle().value

                LaunchedEffect(activeProfile.id) {
                    continueWatchingViewModel.loadContinueWatchingEntities(profileId = activeProfile.id)
                }

                screens.HomeScreen(
                    activeProfile = activeProfile,
                    continueWatchingEntities = continueWatchingEntities,
                    movieEntities = movies,
                    tvEpisodeEntities = tvEpisodes,
                    onEntityClick = { entityId ->
                        navController.navigate(EntityScreen(entityId = entityId))
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

                val lifecycleOwner = LocalLifecycleOwner.current

                // Locks the screen orientation to landscape mode
                LockScreenOrientation(ScreenOrientation.LANDSCAPE)

                LaunchedEffect(entityScreen.entityId) {
                    // Load playback entity
                    playbackEntityViewModel.loadPlaybackEntity(
                        entityId = entityScreen.entityId,
                    )

                    // Listen to app close event
                    lifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
                        override fun onStateChanged(
                            source: LifecycleOwner,
                            event: Lifecycle.Event
                        ) {
                            if (event == Lifecycle.Event.ON_PAUSE) {
                                playbackEntityViewModel.updateIsPlaying(isPlaying = false)
                            }
                        }
                    })
                }

                screens.EntityScreen(
                    entity = playbackEntity.value,
                    isPlaying = isPlaying.value,
                    updateIsPlaying = { newIsPlaying ->
                        playbackEntityViewModel.updateIsPlaying(isPlaying = newIsPlaying)
                    },
                    onUpdatePlaybackPosition = { newPosition ->
                        playbackEntityViewModel.updatePlaybackPosition(
                            newPosition = newPosition,
                        )
                    },
                )
            }
        }
    }
}