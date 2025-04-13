package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    LocalFoundations.current.IconButton(
        onClick = onClick,
        modifier = modifier,
        content = content,
    )
}