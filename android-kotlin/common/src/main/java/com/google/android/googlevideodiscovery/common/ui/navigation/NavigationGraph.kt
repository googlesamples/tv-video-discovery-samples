package com.google.android.googlevideodiscovery.common.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.googlevideodiscovery.common.ui.foundation.Foundations
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalFoundations
import com.google.android.googlevideodiscovery.common.viewmodels.IdentityAndAccountManagementViewModel

@Composable
fun NavigationGraph(
    foundations: Foundations,
    screens: NavigationScreens = remember { NavigationScreensImpl() },
    identityAndAccountManagementViewModel: IdentityAndAccountManagementViewModel = hiltViewModel(),
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
                        identityAndAccountManagementViewModel.performRegistration {
                            postAuth()
                        }
                    },
                    performLogin = {
                        identityAndAccountManagementViewModel.performLogin {
                            postAuth()
                        }
                    }
                )
            }
            composable<ProfilesScreen> {
                val accountState = identityAndAccountManagementViewModel.account.collectAsState()

                accountState.value?.let { account ->
                    screens.ProfilesScreen(
                        account = account,
                        onCreateProfile = {
                            identityAndAccountManagementViewModel.createNewProfile {

                            }
                        },
                        onSelectProfile = {}
                    )
                }
            }
            composable<HomeScreen> { screens.HomeScreen() }
            composable<SettingsScreen> { screens.SettingsScreen() }
            composable<EntityScreen> { screens.EntityScreen() }
        }
    }
}