package com.google.android.googlevideodiscovery.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.googlevideodiscovery.common.ui.foundation.Foundations
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalFoundationsProvider
import com.google.android.googlevideodiscovery.common.viewmodels.AuthViewModel

@Composable
fun NavigationGraph(
    foundations: Foundations,
    screens: NavigationScreens,
    authViewModel: AuthViewModel = viewModel(),
) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalFoundationsProvider provides foundations) {
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
                        authViewModel.performRegistration {
                            postAuth()
                        }
                    },
                    performLogin = {
                        authViewModel.performLogin {
                            postAuth()
                        }
                    }
                )
            }
            composable<ProfilesScreen> { screens.ProfilesScreen() }
            composable<HomeScreen> { screens.HomeScreen() }
            composable<SettingsScreen> { screens.SettingsScreen() }
            composable<EntityScreen> { screens.EntityScreen() }
        }
    }
}