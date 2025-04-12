package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.runtime.Composable

@Suppress("ClassName")
object MaterialTheme {
    private val foundations @Composable get() = LocalFoundations.current

    object colorScheme {
        val onSurfaceVariant
        @Composable get() = foundations.onSurfaceVariant
    }
}