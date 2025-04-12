package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

interface Foundations {
    val onSurfaceVariant: Color
        @Composable get

    @Composable
    fun Text(
        text: String,
        modifier: Modifier,
        style: TextStyle,
    )

    @Composable
    fun Button(
        onClick: () -> Unit,
        modifier: Modifier,
        content: @Composable RowScope.() -> Unit
    )

    @Composable
    fun Surface(
        modifier: Modifier,
        content: @Composable RowScope.() -> Unit
    )

    @Composable
    fun Surface(
        onClick: () -> Unit,
        modifier: Modifier,
        content: @Composable RowScope.() -> Unit
    )
}