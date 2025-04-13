package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Card(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit,
) {
    LocalFoundations.current.Card(
        onClick = onClick,
        modifier = modifier,
        interactionSource = interactionSource,
        content = content
    )
}