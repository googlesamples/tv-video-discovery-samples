package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
) {
    LocalFoundations.current.Icon(
        imageVector = imageVector,
        contentDescription =  contentDescription,
        modifier = modifier,
        tint = tint
    )
}