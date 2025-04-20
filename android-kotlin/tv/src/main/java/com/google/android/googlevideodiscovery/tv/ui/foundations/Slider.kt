package com.google.android.googlevideodiscovery.tv.ui.foundations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme

@Composable
fun TvSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier,
    valueRange: ClosedFloatingPointRange<Float>
) {
    val height = 6.dp
    var isFocused by remember { mutableStateOf(false) }
    val progress = remember(value, valueRange) {
        value / (valueRange.endInclusive - valueRange.start)
    }

    Row(
        modifier = modifier.height(height),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topStart = height / 2,
                        bottomStart = height / 2,
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
        )
        Box(
            modifier = Modifier
                .then(
                    if (isFocused) {
                        Modifier
                            .scale(1.2f)
                            .width(14.dp)
                            .aspectRatio(1f)
                            .border(2.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                            .padding(4.dp)
                            .background(MaterialTheme.colorScheme.onSurface, CircleShape)
                    } else {
                        Modifier
                            .width(14.dp)
                            .aspectRatio(1f)
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                    }
                )
                .onFocusChanged { isFocused = it.isFocused }
                .onKeyEvent {
                    when (it.key) {
                        Key.DirectionLeft -> {
                            onValueChange((value - SCRUB_AMOUNT).coerceIn(valueRange))
                            true
                        }

                        Key.DirectionRight -> {
                            onValueChange((value + SCRUB_AMOUNT).coerceIn(valueRange))
                            true
                        }

                        else -> false
                    }
                }
                .focusable()
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topEnd = height / 2,
                        bottomEnd = height / 2,
                    )
                )
                .background(MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}

private const val SCRUB_AMOUNT = 5
