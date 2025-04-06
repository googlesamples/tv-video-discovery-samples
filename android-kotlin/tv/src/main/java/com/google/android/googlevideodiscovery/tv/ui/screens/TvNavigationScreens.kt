package com.google.android.googlevideodiscovery.tv.ui.screens

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.tv.material3.Button
import androidx.tv.material3.LocalContentColor
import com.google.android.googlevideodiscovery.common.navigation.NavigationScreens
import com.google.android.googlevideodiscovery.common.ui.screens.LoginScreen as CommonLoginScreen

class TvNavigationScreens : NavigationScreens {
    @Composable
    override fun LoginScreen(onLogin: () -> Unit) {
        CommonLoginScreen(
            primaryTextColor = LocalContentColor.current
        ) {
            Button(onClick = onLogin) {
                BasicText("Login")
            }
            Button(onClick = onLogin) {
                BasicText("Register")
            }
        }
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