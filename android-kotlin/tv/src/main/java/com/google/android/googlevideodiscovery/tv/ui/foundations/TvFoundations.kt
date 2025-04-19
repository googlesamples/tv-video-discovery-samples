package com.google.android.googlevideodiscovery.tv.ui.foundations

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.tv.material3.LocalContentColor
import androidx.tv.material3.LocalTextStyle
import androidx.tv.material3.MaterialTheme
import com.google.android.googlevideodiscovery.common.ui.foundation.Foundations

class TvFoundations : Foundations {
    override val localTextStyle: TextStyle @Composable get() = LocalTextStyle.current
    override val localContentColor: Color @Composable get() = LocalContentColor.current

    override val primary: Color @Composable get() = MaterialTheme.colorScheme.primary
    override val onPrimary: Color @Composable get() = MaterialTheme.colorScheme.onPrimary
    override val primaryContainer: Color @Composable get() = MaterialTheme.colorScheme.primaryContainer
    override val onPrimaryContainer: Color @Composable get() = MaterialTheme.colorScheme.onPrimaryContainer
    override val inversePrimary: Color @Composable get() = MaterialTheme.colorScheme.inversePrimary
    override val secondary: Color @Composable get() = MaterialTheme.colorScheme.secondary
    override val onSecondary: Color @Composable get() = MaterialTheme.colorScheme.onSecondary
    override val secondaryContainer: Color @Composable get() = MaterialTheme.colorScheme.secondaryContainer
    override val onSecondaryContainer: Color @Composable get() = MaterialTheme.colorScheme.onSecondaryContainer
    override val tertiary: Color @Composable get() = MaterialTheme.colorScheme.tertiary
    override val onTertiary: Color @Composable get() = MaterialTheme.colorScheme.onTertiary
    override val tertiaryContainer: Color @Composable get() = MaterialTheme.colorScheme.tertiaryContainer
    override val onTertiaryContainer: Color @Composable get() = MaterialTheme.colorScheme.onTertiaryContainer
    override val background: Color @Composable get() = MaterialTheme.colorScheme.background
    override val onBackground: Color @Composable get() = MaterialTheme.colorScheme.onBackground
    override val surface: Color @Composable get() = MaterialTheme.colorScheme.surface
    override val onSurface: Color @Composable get() = MaterialTheme.colorScheme.onSurface
    override val surfaceVariant: Color @Composable get() = MaterialTheme.colorScheme.surfaceVariant
    override val onSurfaceVariant: Color @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
    override val surfaceTint: Color @Composable get() = MaterialTheme.colorScheme.surfaceTint
    override val inverseSurface: Color @Composable get() = MaterialTheme.colorScheme.inverseSurface
    override val inverseOnSurface: Color @Composable get() = MaterialTheme.colorScheme.inverseOnSurface
    override val error: Color @Composable get() = MaterialTheme.colorScheme.error
    override val onError: Color @Composable get() = MaterialTheme.colorScheme.onError
    override val errorContainer: Color @Composable get() = MaterialTheme.colorScheme.errorContainer
    override val onErrorContainer: Color @Composable get() = MaterialTheme.colorScheme.onErrorContainer
    override val border: Color @Composable get() = MaterialTheme.colorScheme.border
    override val borderVariant: Color @Composable get() = MaterialTheme.colorScheme.borderVariant
    override val scrim: Color @Composable get() = MaterialTheme.colorScheme.scrim

    override val titleMedium: TextStyle @Composable get() = MaterialTheme.typography.titleMedium
    override val bodySmall: TextStyle @Composable get() = MaterialTheme.typography.bodySmall
    override val bodyMedium: TextStyle @Composable get() = MaterialTheme.typography.bodyMedium
    override val headlineSmall: TextStyle @Composable get() = MaterialTheme.typography.headlineSmall

    @Composable
    override fun Text(text: String, modifier: Modifier, style: TextStyle) {
        androidx.tv.material3.Text(
            text = text,
            modifier = modifier,
            style = style
        )
    }

    @Composable
    override fun Icon(
        imageVector: ImageVector,
        contentDescription: String?,
        modifier: Modifier,
        tint: Color,
    ) {
        androidx.tv.material3.Icon(
            imageVector,
            contentDescription = contentDescription,
            modifier = modifier,
            tint = tint,
        )
    }

    @Composable
    override fun Button(
        onClick: () -> Unit,
        modifier: Modifier,
        content: @Composable RowScope.() -> Unit
    ) {
        androidx.tv.material3.Button(
            onClick = onClick,
            modifier = modifier,
            content = content
        )
    }

    @Composable
    override fun IconButton(
        onClick: () -> Unit,
        modifier: Modifier,
        content: @Composable () -> Unit
    ) {
        androidx.tv.material3.IconButton(
            onClick = onClick,
            modifier = modifier,
        ) {
            content()
        }
    }

    @Composable
    override fun Surface(
        modifier: Modifier,
        shape: Shape,
        content: @Composable RowScope.() -> Unit
    ) {
        androidx.tv.material3.Surface(modifier = modifier, shape = shape) {
            Row(
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    }

    @Composable
    override fun Surface(
        onClick: () -> Unit,
        modifier: Modifier,
        content: @Composable RowScope.() -> Unit
    ) {
        androidx.tv.material3.Surface(
            onClick = onClick,
            modifier = modifier,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    }

    @Composable
    override fun Card(
        onClick: () -> Unit,
        modifier: Modifier,
        onLongClick: () -> Unit,
        interactionSource: MutableInteractionSource,
        content: @Composable ColumnScope.() -> Unit
    ) {
        androidx.tv.material3.Card(
            onClick = onClick,
            modifier = modifier,
            onLongClick = onLongClick,
            interactionSource = interactionSource,
            content = content,
        )
    }

    @Composable
    override fun Slider(
        value: Float,
        onValueChange: (Float) -> Unit,
        modifier: Modifier,
        valueRange: ClosedFloatingPointRange<Float>
    ) {
        androidx.tv.material3.Text("Slider....")
    }

    @Composable
    override fun ListItem(
        selected: Boolean,
        onClick: () -> Unit,
        headlineContent: @Composable () -> Unit,
        modifier: Modifier,
        overlineContent: @Composable() (() -> Unit)?,
        supportingContent: @Composable() (() -> Unit)?,
        leadingContent: @Composable() (() -> Unit)?,
        trailingContent: @Composable() (() -> Unit)?
    ) {
        androidx.tv.material3.ListItem(
            selected = selected,
            onClick = onClick,
            headlineContent = headlineContent,
            modifier = modifier,
            overlineContent = overlineContent,
            supportingContent = supportingContent,
            leadingContent = { leadingContent?.invoke() },
            trailingContent = trailingContent,
        )
    }
}