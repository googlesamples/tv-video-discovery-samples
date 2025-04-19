package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.runtime.Composable

@Suppress("ClassName")
object MaterialTheme {
    object colorScheme {
        val primary @Composable get() = LocalFoundations.current.primary
        val onPrimary @Composable get() = LocalFoundations.current.onPrimary
        val primaryContainer @Composable get() = LocalFoundations.current.primaryContainer
        val onPrimaryContainer @Composable get() = LocalFoundations.current.onPrimaryContainer
        val inversePrimary @Composable get() = LocalFoundations.current.inversePrimary
        val secondary @Composable get() = LocalFoundations.current.secondary
        val onSecondary @Composable get() = LocalFoundations.current.onSecondary
        val secondaryContainer @Composable get() = LocalFoundations.current.secondaryContainer
        val onSecondaryContainer @Composable get() = LocalFoundations.current.onSecondaryContainer
        val tertiary @Composable get() = LocalFoundations.current.tertiary
        val onTertiary @Composable get() = LocalFoundations.current.onTertiary
        val tertiaryContainer @Composable get() = LocalFoundations.current.tertiaryContainer
        val onTertiaryContainer @Composable get() = LocalFoundations.current.onTertiaryContainer
        val background @Composable get() = LocalFoundations.current.background
        val onBackground @Composable get() = LocalFoundations.current.onBackground
        val surface @Composable get() = LocalFoundations.current.surface
        val onSurface @Composable get() = LocalFoundations.current.onSurface
        val surfaceVariant @Composable get() = LocalFoundations.current.surfaceVariant
        val onSurfaceVariant @Composable get() = LocalFoundations.current.onSurfaceVariant
        val surfaceTint @Composable get() = LocalFoundations.current.surfaceTint
        val inverseSurface @Composable get() = LocalFoundations.current.inverseSurface
        val inverseOnSurface @Composable get() = LocalFoundations.current.inverseOnSurface
        val error @Composable get() = LocalFoundations.current.error
        val onError @Composable get() = LocalFoundations.current.onError
        val errorContainer @Composable get() = LocalFoundations.current.errorContainer
        val onErrorContainer @Composable get() = LocalFoundations.current.onErrorContainer
        val border @Composable get() = LocalFoundations.current.border
        val borderVariant @Composable get() = LocalFoundations.current.borderVariant
        val scrim @Composable get() = LocalFoundations.current.scrim
    }

    object typography {
        val titleMedium @Composable get() = LocalFoundations.current.titleMedium
        val bodySmall @Composable get() = LocalFoundations.current.bodySmall
        val bodyMedium @Composable get() = LocalFoundations.current.bodyMedium
        val headlineSmall @Composable get() = LocalFoundations.current.headlineSmall
    }
}