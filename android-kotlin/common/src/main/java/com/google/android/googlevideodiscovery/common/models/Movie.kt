package com.google.android.googlevideodiscovery.common.models

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class Movie(
    val id: String,
    val name: String,
    val duration: Duration,
    val playbackUris: PlatformSpecificUris,
    val images: List<Image>,
    val releaseYear: Int,
    val genre: String,
    var nextMovie: Movie?,
)

fun Movie.toPlaybackEntity() = PlaybackEntity(
    entityId = id,
    title = name,
    releaseYear = releaseYear,
    duration = duration,
    playbackPosition = 0.seconds
)
