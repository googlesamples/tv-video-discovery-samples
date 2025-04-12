package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object LocalContentColor {
    val current: Color
        @Composable get() = LocalFoundations.current.localContentColor
}