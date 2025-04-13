package com.google.android.googlevideodiscovery.common.ui.screens

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.ui.foundation.Icon
import com.google.android.googlevideodiscovery.common.ui.foundation.IconButton
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalContentColor
import com.google.android.googlevideodiscovery.common.ui.foundation.Slider
import com.google.android.googlevideodiscovery.common.ui.foundation.Surface
import com.google.android.googlevideodiscovery.common.ui.foundation.Text
import com.google.android.googlevideodiscovery.common.ui.utils.LockScreenOrientation
import com.google.android.googlevideodiscovery.common.ui.utils.ScreenOrientation
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
internal fun EntityScreen(entity: PlaybackEntity?) {
    LockScreenOrientation(ScreenOrientation.LANDSCAPE)

    if (entity == null) {
        LoadingEntityScreen()
        return
    }

    EntityPlayer(entity)
}

@Composable
private fun LoadingEntityScreen() {
    Box(Modifier.fillMaxSize()) {
        Text("Loading...", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun EntityPlayer(entity: PlaybackEntity) {
    var isPlaying by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(60.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.TopStart)) {
                Text(entity.title, fontSize = 32.sp)
                Text("${entity.releaseYear}", color = LocalContentColor.current.copy(alpha = 0.6f))
            }

            if (isPlaying) {
                PauseButton(
                    onClick = { isPlaying = false },
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                PlayButton(
                    onClick = { isPlaying = true },
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            ProgressBar(
                duration = entity.duration,
                isPlaying = isPlaying,
                modifier = Modifier.align(Alignment.BottomStart),
                startFrom = entity.startFrom,
            )
        }
    }
}

@Composable
private fun PauseButton(onClick: () -> Unit, modifier: Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(Icons.Default.Pause, contentDescription = null)
    }
}

@Composable
private fun PlayButton(onClick: () -> Unit, modifier: Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(Icons.Default.PlayArrow, contentDescription = null)
    }
}

@Composable
private fun ProgressBar(
    duration: Duration,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    startFrom: Duration? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var value by remember { mutableStateOf(startFrom ?: 0.seconds) }

        LaunchedEffect(isPlaying) {
            if (!isPlaying) {
                return@LaunchedEffect
            }

            while (true) {
                delay(1.seconds)
                value += 1.seconds
            }
        }

        Text(DateUtils.formatElapsedTime(value.inWholeSeconds))

        Slider(
            value = value.inWholeSeconds.toFloat(),
            onValueChange = { value = it.toInt().toDuration(DurationUnit.SECONDS) },
            modifier = Modifier.weight(1f),
            valueRange = 0f..duration.inWholeSeconds.toFloat(),
        )

        Text(DateUtils.formatElapsedTime(duration.inWholeSeconds))
    }
}