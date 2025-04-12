package com.google.android.googlevideodiscovery.common.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.googlevideodiscovery.common.ui.foundation.Foundations
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalFoundations
import com.google.android.googlevideodiscovery.common.viewmodels.IdentityAndAccountManagementViewModel
import com.google.android.googlevideodiscovery.common.viewmodels.MediaContentViewModel

@Composable
fun NavigationGraph(
    foundations: Foundations,
    screens: NavigationScreens = remember { NavigationScreensImpl() },
    iamViewModel: IdentityAndAccountManagementViewModel = hiltViewModel(),
    mediaContentViewModel: MediaContentViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalFoundations provides foundations) {
        NavHost(navController = navController, startDestination = LoginScreen) {
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
                    tvEpisodes = tvEpisodes
                )
            }
            composable<SettingsScreen> { screens.SettingsScreen() }
            composable<EntityScreen> { screens.EntityScreen() }
        }
    }
}