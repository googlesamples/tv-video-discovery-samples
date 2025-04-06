package com.google.android.googlevideodiscovery.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.googlevideodiscovery.common.ui.foundation.Foundations
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalFoundationsProvider

@Composable
fun NavigationGraph(
    foundations: Foundations,
    screens: NavigationScreens,
) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalFoundationsProvider provides foundations) {
        NavHost(navController = navController, startDestination = LoginScreen) {
            composable<LoginScreen> {
                screens.LoginScreen(
                    onLogin = {
                        navController.navigate(ProfilesScreen) {
                            popUpTo(LoginScreen) {
                                inclusive = true
                            }
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