package com.google.android.googlevideodiscovery.common.navigation

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

interface NavigationScreens {
    @Composable
    fun LoginScreen(onLogin: () -> Unit)

    @Composable
    fun HomeScreen()

    @Composable
    fun SettingsScreen()

    @Composable
    fun EntityScreen()
}

@Serializable
internal object LoginScreen

@Serializable
internal object HomeScreen

@Serializable
internal object SettingsScreen

@Serializable
data class EntityScreen(val entityId: String)
