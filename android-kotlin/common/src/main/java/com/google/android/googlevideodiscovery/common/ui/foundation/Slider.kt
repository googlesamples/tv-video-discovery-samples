package com.google.android.googlevideodiscovery.common.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
) {
    LocalFoundations.current.Slider(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        valueRange = valueRange
    )
}