package com.google.android.googlevideodiscovery.tv.ui.screens

import androidx.compose.runtime.Composable
import com.google.android.googlevideodiscovery.common.navigation.NavigationScreens

class TvNavigationScreens : NavigationScreens {
    @Composable
    override fun LoginScreen(onLogin: () -> Unit) {
        LoginScreen()
    }

    @Composable
    override fun HomeScreen() {
        HomeScreen()
    }

    @Composable
    override fun SettingsScreen() {
        SettingsScreen()
    }

    @Composable
    override fun EntityScreen() {
        EntityScreen()
    }

}