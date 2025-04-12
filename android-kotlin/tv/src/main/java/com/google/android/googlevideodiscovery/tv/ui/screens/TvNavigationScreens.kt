package com.google.android.googlevideodiscovery.tv.ui.screens

import androidx.compose.runtime.Composable
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.navigation.NavigationScreens
import com.google.android.googlevideodiscovery.common.ui.screens.LoginScreen as CommonLoginScreen
import com.google.android.googlevideodiscovery.common.ui.screens.ProfilesScreen as CommonProfilesScreen

class TvNavigationScreens : NavigationScreens {
    @Composable
    override fun LoginScreen(
        performRegistration: () -> Unit,
        performLogin: () -> Unit
    ) {
        CommonLoginScreen(performRegistration = performRegistration, performLogin = performLogin)
    }

    @Composable
    override fun ProfilesScreen(account: Account) {
        CommonProfilesScreen(account = account)
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