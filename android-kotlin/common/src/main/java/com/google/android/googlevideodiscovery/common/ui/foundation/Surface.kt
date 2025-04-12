package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val foundations = LocalFoundations.current

    foundations.Surface(
        modifier = modifier,
        content = content
    )
}

@Composable
fun Surface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val foundations = LocalFoundations.current

    foundations.Surface(
        onClick = onClick,
        modifier = modifier,
        content = content
    )
}