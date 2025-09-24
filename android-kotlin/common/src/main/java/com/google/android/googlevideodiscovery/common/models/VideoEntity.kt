package com.google.android.googlevideodiscovery.common.models

import java.time.Instant
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

sealed interface VideoEntity {
    val id: String
    val name: String
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
    val images: List<Image>,
    val duration: Duration,
    val playbackUris: PlatformSpecificUris,
    val releaseYear: Int,
    val genre: String,
    var nextMovieEntity: MovieEntity?,
) : VideoEntity {
    override val type = EntityType.Movie
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
    val images: List<Image>,
    val duration: Duration,
    val playbackUris: PlatformSpecificUris,
    val releaseYear: Int,
    val genre: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val showTitle: String,
    var nextEpisode: TvEpisodeEntity?,
) : VideoEntity {
    override val type = EntityType.TvEpisode

    override fun toPlaybackEntity(playbackPosition: Duration?) = PlaybackEntity(
        entityId = id,
        title = name,
        duration = duration,
        releaseYear = releaseYear,
        genre = genre,
        playbackPosition = playbackPosition ?: 0.seconds
    )
}

data class VideoClipEntity(
    override val id: String,
    override val name: String,
    val images: List<Image>,
    val duration: Duration,
    val playbackUris: PlatformSpecificUris,
    val releaseYear: Int,
    val genre: String,
    val creatorImage: String,
    val creator: String,
    var nextVideoClipEntity: VideoClipEntity?,
) : VideoEntity {
    override val type = EntityType.VideoClip

    override fun toPlaybackEntity(playbackPosition: Duration?) = PlaybackEntity(
        entityId = id,
        title = name,
        duration = duration,
        releaseYear = releaseYear,
        genre = genre,
        playbackPosition = playbackPosition ?: 0.seconds
    )
}

data class TvShowEntity(
    override val id: String,
    override val name: String,
    val images: List<Image>,
    val description: String,
    val platformSpecificUris: PlatformSpecificUris,
    val releaseYear: Int,
    val genre: String,
    val seasonCount: Integer,
    val duration: Duration,
    var nextTvShowEntity: TvShowEntity?,
) : VideoEntity {
    override val type = EntityType.TvShow

    override fun toPlaybackEntity(playbackPosition: Duration?) = PlaybackEntity(
        entityId = id,
        title = name,
        duration = duration,
        releaseYear = releaseYear,
        genre = genre,
        playbackPosition = playbackPosition ?: 0.seconds
    )
}
