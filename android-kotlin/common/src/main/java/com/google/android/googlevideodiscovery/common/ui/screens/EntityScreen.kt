package com.google.android.googlevideodiscovery.common.ui.screens

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.engage.converters.PublishContinuationClusterReason
import com.google.android.googlevideodiscovery.common.ui.foundation.Icon
import com.google.android.googlevideodiscovery.common.ui.foundation.IconButton
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalContentColor
import com.google.android.googlevideodiscovery.common.ui.foundation.Slider
import com.google.android.googlevideodiscovery.common.ui.foundation.Surface
import com.google.android.googlevideodiscovery.common.ui.foundation.Text
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
internal fun EntityScreen(
    playerTitleContent: @Composable BoxScope.() -> Unit,
    playPauseButtonContent: @Composable BoxScope.() -> Unit,
    progressBarContent: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(60.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            playerTitleContent()

            playPauseButtonContent()

            progressBarContent()
        }
    }
}

internal object EntityScreenDefaults {
    @Composable
    fun BoxScope.PlayerHeading(entityTitle: String, entityReleaseYear: Int) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Text(entityTitle, fontSize = 32.sp)
            Text("$entityReleaseYear", color = LocalContentColor.current.copy(alpha = 0.6f))
        }
    }

    @Composable
    fun BoxScope.PauseButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
        IconButton(onClick = onClick, modifier = modifier.align(Alignment.Center)) {
            Icon(Icons.Default.Pause, contentDescription = null)
        }
    }

    @Composable
    fun BoxScope.PlayButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
        IconButton(onClick = onClick, modifier = modifier.align(Alignment.Center)) {
            Icon(Icons.Default.PlayArrow, contentDescription = null)
        }
    }

    @Composable
    fun BoxScope.ProgressBar(
        duration: Duration,
        isPlaying: Boolean,
        onPlaybackEnd: () -> Unit,
        modifier: Modifier = Modifier,
        currentPosition: Duration,
        onUpdatePlaybackPosition: (Duration, PublishContinuationClusterReason?) -> Unit
    ) {
        val updatedCurrentPosition = rememberUpdatedState(currentPosition)

        Row(
            modifier = modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            LaunchedEffect(isPlaying) {
                if (isPlaying) {
                    while (true) {
                        delay(1.seconds)

                        val newPlaybackPosition = (updatedCurrentPosition.value + 1.seconds)
                        onUpdatePlaybackPosition(
                            newPlaybackPosition.coerceAtMost(duration),
                            null
                        )

                        if (newPlaybackPosition >= duration) {
                            onPlaybackEnd()
                            break
                        }
                    }
                }
            }

            Text(DateUtils.formatElapsedTime(currentPosition.inWholeSeconds))

            Slider(
                value = currentPosition.inWholeSeconds.toFloat(),
                onValueChange = {
                    onUpdatePlaybackPosition(
                        it.toInt().toDuration(DurationUnit.SECONDS),
                        PublishContinuationClusterReason.USER_SCRUBBED_VIDEO
                    )
                },
                modifier = Modifier.weight(1f),
                valueRange = 0f..duration.inWholeSeconds.toFloat(),
            )

            Text(DateUtils.formatElapsedTime(duration.inWholeSeconds))
        }
    }

    @Composable
    fun LoadingEntityScreen() {
        Box(Modifier.fillMaxSize()) {
            Text("Loading...", modifier = Modifier.align(Alignment.Center))
        }
    }
}
