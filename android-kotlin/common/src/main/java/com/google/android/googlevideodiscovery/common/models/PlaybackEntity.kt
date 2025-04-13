package com.google.android.googlevideodiscovery.common.models

import kotlin.time.Duration

data class PlaybackEntity(
    val entityId: String,
    val title: String,
    val releaseYear: Int,
    val duration: Duration,
    var startFrom: Duration?
)
