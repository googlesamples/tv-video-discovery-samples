package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.runtime.compositionLocalOf

internal val LocalFoundationsProvider = compositionLocalOf<Foundations> {
    throw RuntimeException("Foundations are not provided")
}