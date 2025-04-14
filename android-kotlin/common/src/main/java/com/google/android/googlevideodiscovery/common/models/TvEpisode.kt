package com.google.android.googlevideodiscovery.common.models

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class TvEpisode(
    val id: String,
    val name: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val showTitle: String,
    val duration: Duration,
    val playbackUris: PlatformSpecificUris,
    val images: List<Image>,
    val releaseYear: Int,
    val genre: String,
    var nextEpisode: TvEpisode?,
)

fun TvEpisode.toPlaybackEntity() = PlaybackEntity(
    entityId = id,
    title = name,
    releaseYear = releaseYear,
    duration = duration,
    playbackPosition = 0.seconds
)
