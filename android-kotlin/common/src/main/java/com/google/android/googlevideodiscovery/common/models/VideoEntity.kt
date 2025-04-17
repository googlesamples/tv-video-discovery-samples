package com.google.android.googlevideodiscovery.common.models

import java.time.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

sealed interface VideoEntity {
    val id: String
    val duration: Duration
    val name: String
    val playbackUris: PlatformSpecificUris
    val images: List<Image>
    val releaseYear: Int
    val genre: String
    val type: EntityType

    fun toContinueWatchingEntity(
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

    fun toPlaybackEntity(playbackPosition: Duration?): PlaybackEntity
}

data class MovieEntity(
    override val id: String,
    override val name: String,
    override val duration: Duration,
    override val playbackUris: PlatformSpecificUris,
    override val images: List<Image>,
    override val releaseYear: Int,
    override val genre: String,
    var nextMovieEntity: MovieEntity?,
) : VideoEntity {
    override val type = EntityType.MOVIE

    fun toPlaybackEntity() = toPlaybackEntity(playbackPosition = null)
    override fun toPlaybackEntity(playbackPosition: Duration?) = PlaybackEntity(
        entityId = id,
        title = name,
        releaseYear = releaseYear,
        duration = duration,
        genre = genre,
        playbackPosition = playbackPosition ?: 0.seconds
    )
}

data class TvEpisodeEntity(
    override val id: String,
    override val name: String,
    override val duration: Duration,
    override val playbackUris: PlatformSpecificUris,
    override val images: List<Image>,
    override val releaseYear: Int,
    override val genre: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val showTitle: String,
    var nextEpisode: TvEpisodeEntity?,
) : VideoEntity {
    override val type = EntityType.TV_EPISODE

    fun toPlaybackEntity() = toPlaybackEntity(playbackPosition = null)
    override fun toPlaybackEntity(playbackPosition: Duration?) = PlaybackEntity(
        entityId = id,
        title = name,
        releaseYear = releaseYear,
        duration = duration,
        genre = genre,
        playbackPosition = playbackPosition ?: 0.seconds
    )
}
