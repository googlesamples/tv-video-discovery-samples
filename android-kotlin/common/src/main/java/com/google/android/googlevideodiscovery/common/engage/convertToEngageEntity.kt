package com.google.android.googlevideodiscovery.common.engage

import com.google.android.engage.common.datamodel.Entity
import com.google.android.engage.video.datamodel.MovieEntity
import com.google.android.engage.video.datamodel.TvEpisodeEntity
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.Movie
import com.google.android.googlevideodiscovery.common.models.TvEpisode

internal fun ContinueWatchingEntity.convertToEngageEntity(): Entity = when (entity) {
    is Movie -> entity.convertToEngageMovieEntity(this)
    is TvEpisode -> entity.convertToEngageTvEpisodeEntity(this)
}

private fun Movie.convertToEngageMovieEntity(continueWatchingEntity: ContinueWatchingEntity) =
    MovieEntity.Builder()
        .setEntityId(id)
        .setName(name)
        .addGenre(genre)
        .setDurationMillis(duration.inWholeMilliseconds)
        .setLastEngagementTimeMillis(continueWatchingEntity.lastEngagementTimeMillis)
        .setWatchNextType(continueWatchingEntity.continueWatchingType.convertToEngageWatchNextType())
        .setLastPlayBackPositionTimeMillis(continueWatchingEntity.playbackPosition.inWholeMilliseconds)
        .addPlatformSpecificPlaybackUris(
            constructPlaybackUris(
                entityId = id,
                playbackPositionMillis = continueWatchingEntity.playbackPosition.inWholeMilliseconds
            )
        )
        .build()

private fun TvEpisode.convertToEngageTvEpisodeEntity(continueWatchingEntity: ContinueWatchingEntity) =
    TvEpisodeEntity.Builder()
        .setEntityId(id)
        .setName(name)
        .setShowTitle(showTitle)
        .setSeasonNumber(seasonNumber.toString())
        .setEpisodeDisplayNumber(episodeNumber.toString())
        .setLastEngagementTimeMillis(continueWatchingEntity.lastEngagementTimeMillis)
        .addGenre(genre)
        .setDurationMillis(duration.inWholeMilliseconds)
        .setWatchNextType(continueWatchingEntity.continueWatchingType.convertToEngageWatchNextType())
        .setLastPlayBackPositionTimeMillis(continueWatchingEntity.playbackPosition.inWholeMilliseconds)
        .addPlatformSpecificPlaybackUris(
            constructPlaybackUris(
                entityId = id,
                playbackPositionMillis = continueWatchingEntity.playbackPosition.inWholeMilliseconds
            )
        )
        .build()
