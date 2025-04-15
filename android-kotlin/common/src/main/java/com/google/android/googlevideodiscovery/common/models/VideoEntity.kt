package com.google.android.googlevideodiscovery.common.models

import java.time.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

sealed interface VideoEntity

data class Movie(
    val id: String,
    val name: String,
    val duration: Duration,
    val playbackUris: PlatformSpecificUris,
    val images: List<Image>,
    val releaseYear: Int,
    val genre: String,
    var nextMovie: Movie?,
) : VideoEntity

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
) : VideoEntity

fun Movie.toPlaybackEntity() = PlaybackEntity(
    entityId = id,
    title = name,
    releaseYear = releaseYear,
    duration = duration,
    genre = genre,
    playbackPosition = 0.seconds
)

fun TvEpisode.toPlaybackEntity() = PlaybackEntity(
    entityId = id,
    title = name,
    releaseYear = releaseYear,
    duration = duration,
    genre = genre,
    playbackPosition = 0.seconds
)

fun Movie.toContinueWatchingEntity(
    continueWatchingType: ContinueWatchingType,
    profileId: String,
    playbackPosition: Duration,
    lastEngagementTime: Instant
) = ContinueWatchingEntity(
    entity = this,
    continueWatchingType = continueWatchingType,
    playbackPosition = playbackPosition,
    profileId = profileId,
    lastEngagementTimeMillis = lastEngagementTime.toEpochMilli()
)

fun TvEpisode.toContinueWatchingEntity(
    continueWatchingType: ContinueWatchingType,
    profileId: String,
    playbackPosition: Duration,
    lastEngagementTime: Instant
) = ContinueWatchingEntity(
    entity = this,
    continueWatchingType = continueWatchingType,
    playbackPosition = playbackPosition,
    profileId = profileId,
    lastEngagementTimeMillis = lastEngagementTime.toEpochMilli()
)
