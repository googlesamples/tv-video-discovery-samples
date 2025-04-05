package com.google.android.googlevideodiscovery.tv.ui.screens

import androidx.compose.runtime.Composable
import com.google.android.googlevideodiscovery.common.navigation.NavigationScreens

class TvNavigationScreens : NavigationScreens {
    @Composable
    override fun LoginScreen(onLogin: () -> Unit) {
        TvLoginScreen(onLogin = onLogin)
    }

    @Composable
    override fun ProfilesScreen() {
        TvProfilesScreen()
    }

    @Composable
    override fun HomeScreen() {
        TvHomeScreen()
    }

    @Composable
    override fun SettingsScreen() {
        TvSettingsScreen()
    }

    @Composable
    override fun EntityScreen() {
        TvEntityScreen()
    }

}