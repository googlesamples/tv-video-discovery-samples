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
import androidx.navigation.navDeepLink
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
            composable<DeeplinkHandlerScreen>(
                deepLinks = listOf(
                    navDeepLink<DeeplinkHandlerScreen>(basePath = "https://googletvvideodiscovery.com"),
                    navDeepLink<DeeplinkHandlerScreen>(basePath = "https://googletvvideodiscovery.com?entityId={entityId}&profileId={profileId}"),
                )
            ) {
                val entityId = it.toRoute<DeeplinkHandlerScreen>().entityId
                val profileId = it.toRoute<DeeplinkHandlerScreen>().profileId ?: return@composable

                val iamViewModel = hiltViewModel<IdentityAndAccountManagementViewModel>()
                val loggedInAccount =
                    iamViewModel.loggedInAccount.collectAsStateWithLifecycle().value

                LaunchedEffect(entityId, profileId, loggedInAccount) {
                    val profile = iamViewModel.getProfileById(profileId) ?: return@LaunchedEffect
                    if (loggedInAccount == null) {
                        navController.navigate(
                            LoginScreen
                        ) {
                            popUpTo(DeeplinkHandlerScreen) {
                                inclusive = true
                            }
                        }
                    } else if (loggedInAccount.id != profile.account.id) {
                        navController.navigate(ProfilesScreen) {
                            popUpTo(DeeplinkHandlerScreen) {
                                inclusive = true
                            }
                        }
                    } else {
                        iamViewModel.selectProfile(profile) {
                            navController.navigate(
                                EntityScreen(entityId = entityId)
                            ) {
                                popUpTo(DeeplinkHandlerScreen) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            }

            composable<LoginScreen> {
//                val postLoginDeepLink = it.toRoute<LoginScreen>().postLoginNavigationDeeplink

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
                        },
                        deleteCurrentAccount = {
                            iamViewModel.deleteCurrentAccount()
                            navController.navigate(LoginScreen) {
                                popUpTo(ProfilesScreen) {
                                    inclusive = true
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

                val loggedInAccount =
                    iamViewModel.loggedInAccount.collectAsStateWithLifecycle(null).value
                        ?: return@composable
                val activeProfile = iamViewModel.activeProfile.collectAsStateWithLifecycle().value
                    ?: return@composable

                val continueWatchingEntities =
                    continueWatchingViewModel.continueWatchingEntities.collectAsStateWithLifecycle(
                        emptyList()
                    ).value
                val movies = mediaContentViewModel.movies.collectAsStateWithLifecycle().value
                val tvEpisodes =
                    mediaContentViewModel.tvEpisodes.collectAsStateWithLifecycle().value

                screens.HomeScreen(
                    loggedInAccount = loggedInAccount,
                    activeProfile = activeProfile,
                    continueWatchingEntities = continueWatchingEntities,
                    movieEntities = movies,
                    tvEpisodeEntities = tvEpisodes,
                    onConfirmRemoveFromContinueWatchingRow = { continueWatchingEntity ->
                        continueWatchingViewModel.removeFromContinueWatching(continueWatchingEntity = continueWatchingEntity)
                    },
                    openProfileSelectionPage = {
                        navController.navigate(ProfilesScreen) {
                            popUpTo(HomeScreen) {
                                inclusive = true
                            }
                        }
                    },
                    deleteCurrentProfile = {
                        iamViewModel.deleteCurrentProfile()
                        navController.navigate(ProfilesScreen) {
                            popUpTo(HomeScreen) {
                                inclusive = true
                            }
                        }
                    },
                    logout = {
                        iamViewModel.performLogout()
                        navController.navigate(LoginScreen) {
                            popUpTo(HomeScreen) {
                                inclusive = true
                            }
                        }
                    },
                    updateUserConsentToShareDataWithGoogle = { newConsentValue ->
                        iamViewModel.updateSyncAcrossDevicesConsentValue(newConsentValue)
                    },
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
                    onUpdatePlaybackPosition = { newPosition, reason ->
                        playbackEntityViewModel.updatePlaybackPosition(
                            newPosition = newPosition,
                            reason = reason,
                        )
                    },
                )
            }
        }
    }
}