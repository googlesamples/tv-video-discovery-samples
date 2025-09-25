package com.google.android.googlevideodiscovery.common.engage.converters

import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.MovieEntity
import com.google.android.googlevideodiscovery.common.models.TvEpisodeEntity
import com.google.android.googlevideodiscovery.common.models.VideoClipEntity
import com.google.android.engage.common.datamodel.Entity as EngageEntity
import com.google.android.engage.video.datamodel.MovieEntity as EngageMovieEntity
import com.google.android.engage.video.datamodel.TvEpisodeEntity as EngageTvEpisodeEntity
import com.google.android.engage.video.datamodel.VideoClipEntity as EngageVideoClipEntity

internal fun ContinueWatchingEntity.convertToEngageEntity(): EngageEntity = when (entity) {
    is MovieEntity -> entity.convertToEngageMovieEntity(this)
    is TvEpisodeEntity -> entity.convertToEngageTvEpisodeEntity(this)
    is VideoClipEntity -> entity.convertToEngageVideoClipEntity(this)
    else -> throw IllegalStateException("Unsupported video type for Continue Watching: $entity")
}

private fun MovieEntity.convertToEngageMovieEntity(continueWatchingEntity: ContinueWatchingEntity) =
    EngageMovieEntity.Builder()
        .setEntityId(id)
        .setName(name)
        .addGenre(genre)
        .setDurationMillis(duration.inWholeMilliseconds)
        .setLastEngagementTimeMillis(continueWatchingEntity.lastEngagementTimeMillis)
        .setWatchNextType(continueWatchingEntity.continueWatchingType.convertToEngageWatchNextType())
        .setLastPlayBackPositionTimeMillis(continueWatchingEntity.playbackPosition.inWholeMilliseconds)
        .addPlatformSpecificPlaybackUris(
            constructPlaybackUris(
                profileId = continueWatchingEntity.profileId,
                playbackUris = playbackUris
            )
        )
        .build()

private fun TvEpisodeEntity.convertToEngageTvEpisodeEntity(continueWatchingEntity: ContinueWatchingEntity) =
    EngageTvEpisodeEntity.Builder()
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
                profileId = continueWatchingEntity.profileId,
                playbackUris = playbackUris
            )
        )
        .build()

private fun VideoClipEntity.convertToEngageVideoClipEntity(continueWatchingEntity: ContinueWatchingEntity) =
    EngageVideoClipEntity.Builder()
        .setEntityId(id)
        .setName(name)
        .setLastEngagementTimeMillis(continueWatchingEntity.lastEngagementTimeMillis)
        .setDurationMillis(duration.inWholeMilliseconds)
        .setWatchNextType(continueWatchingEntity.continueWatchingType.convertToEngageWatchNextType())
        .setLastPlayBackPositionTimeMillis(continueWatchingEntity.playbackPosition.inWholeMilliseconds)
        .addPlatformSpecificPlaybackUris(
            constructPlaybackUris(
                profileId = continueWatchingEntity.profileId,
                playbackUris = playbackUris
            )
        )
        .build()
