package com.google.android.googlevideodiscovery.mobile.ui.foundations

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.google.android.googlevideodiscovery.common.ui.foundation.Foundations

class MobileFoundations : Foundations {
    override val onSurfaceVariant: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    override fun Text(text: String) {
        androidx.compose.material3.Text(text = text)
    }

    @Composable
    override fun Text(text: String, modifier: Modifier) {
        androidx.compose.material3.Text(text = text, modifier = modifier)
    }

    @Composable
    override fun Text(text: String, style: TextStyle) {
        androidx.compose.material3.Text(text = text, style = style)
    }

    @Composable
    override fun Text(text: String, modifier: Modifier, style: TextStyle) {
        androidx.compose.material3.Text(text = text, modifier = modifier, style = style)
    }

    @Composable
    override fun Button(onClick: () -> Unit, content: @Composable (RowScope.() -> Unit)) {
        androidx.compose.material3.Button(
            onClick = onClick,
            content = content
        )
    }

    @Composable
    override fun Button(
        onClick: () -> Unit,
        modifier: Modifier,
        content: @Composable() (RowScope.() -> Unit)
    ) {
        androidx.compose.material3.Button(
            onClick = onClick,
            modifier = modifier,
            content = content
        )
    }

    @Composable
    override fun Surface(content: @Composable() (RowScope.() -> Unit)) {
        androidx.compose.material3.Surface {
            Row(content = content)
        }
    }

    @Composable
    override fun Surface(modifier: Modifier, content: @Composable() (RowScope.() -> Unit)) {
        androidx.compose.material3.Surface(modifier = modifier) {
            Row(content = content)
        }
    }

    @Composable
    override fun Surface(onClick: () -> Unit, content: @Composable() (RowScope.() -> Unit)) {
        androidx.compose.material3.Surface(onClick = onClick) {
            Row(content = content)
        }
    }

    @Composable
    override fun Surface(
        onClick: () -> Unit,
        modifier: Modifier,
        content: @Composable() (RowScope.() -> Unit)
    ) {
        androidx.compose.material3.Surface(onClick = onClick, modifier = modifier) {
            Row(content = content)
        }
    }

}