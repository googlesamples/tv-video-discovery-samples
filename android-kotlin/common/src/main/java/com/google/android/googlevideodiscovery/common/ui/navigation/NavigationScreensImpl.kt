package com.google.android.googlevideodiscovery.common.ui.navigation

import androidx.compose.runtime.Composable
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.ui.screens.ProfilesScreenDefaults
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreen as CommonEntityScreen
import com.google.android.googlevideodiscovery.common.ui.screens.HomeScreen as CommonHomeScreen
import com.google.android.googlevideodiscovery.common.ui.screens.LoginScreen as CommonLoginScreen
import com.google.android.googlevideodiscovery.common.ui.screens.ProfilesScreen as CommonProfilesScreen
import com.google.android.googlevideodiscovery.common.ui.screens.SettingsScreen as CommonSettingsScreen

class NavigationScreensImpl : NavigationScreens {
    @Composable
    override fun LoginScreen(
        performRegistration: () -> Unit,
        performLogin: () -> Unit
    ) {
        CommonLoginScreen(
            performRegistration = performRegistration,
            performLogin = performLogin
        )
    }

    @Composable
    override fun ProfilesScreen(
        account: Account,
        onCreateProfile: () -> Unit,
        onSelectProfile: (AccountProfile) -> Unit,
    ) {
        CommonProfilesScreen(accountName = account.name) {
            ProfilesScreenDefaults.ProfilesGrid(
                profiles = account.profiles,
                onCreateProfile = onCreateProfile,
                onSelectProfile = onSelectProfile,
            )
        }
    }

    @Composable
    override fun HomeScreen() {
        CommonHomeScreen()
    }

    @Composable
    override fun EntityScreen() {
        CommonEntityScreen()
    }

    @Composable
    override fun SettingsScreen() {
        CommonSettingsScreen()
    }
}