package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle

interface Foundations {
    val localContentColor: Color @Composable get

    val primary: Color @Composable get
    val onPrimary: Color @Composable get
    val primaryContainer: Color @Composable get
    val onPrimaryContainer: Color @Composable get
    val inversePrimary: Color @Composable get
    val secondary: Color @Composable get
    val onSecondary: Color @Composable get
    val secondaryContainer: Color @Composable get
    val onSecondaryContainer: Color @Composable get
    val tertiary: Color @Composable get
    val onTertiary: Color @Composable get
    val tertiaryContainer: Color @Composable get
    val onTertiaryContainer: Color @Composable get
    val background: Color @Composable get
    val onBackground: Color @Composable get
    val surface: Color @Composable get
    val onSurface: Color @Composable get
    val surfaceVariant: Color @Composable get
    val onSurfaceVariant: Color @Composable get
    val surfaceTint: Color @Composable get
    val inverseSurface: Color @Composable get
    val inverseOnSurface: Color @Composable get
    val error: Color @Composable get
    val onError: Color @Composable get
    val errorContainer: Color @Composable get
    val onErrorContainer: Color @Composable get
    val border: Color @Composable get
    val borderVariant: Color @Composable get
    val scrim: Color @Composable get

    val titleMedium: TextStyle @Composable get
    val bodySmall: TextStyle @Composable get

    @Composable
    fun Text(
        text: String,
        modifier: Modifier,
        style: TextStyle,
    )

    @Composable
    fun Icon(
        imageVector: ImageVector,
        contentDescription: String?,
        modifier: Modifier,
        tint: Color,
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

    @Composable
    fun Card(
        onClick: () -> Unit,
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        content: @Composable ColumnScope.() -> Unit,
    )
}