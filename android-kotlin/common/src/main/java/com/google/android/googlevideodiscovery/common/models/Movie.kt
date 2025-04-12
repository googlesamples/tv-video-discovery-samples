package com.google.android.googlevideodiscovery.common.models

import kotlin.time.Duration

data class Movie(
    val id: String,
    val name: String,
    val duration: Duration,
    val playbackUris: PlatformSpecificUris,
    val images: List<Image>,
    var nextMovie: Movie?
)
