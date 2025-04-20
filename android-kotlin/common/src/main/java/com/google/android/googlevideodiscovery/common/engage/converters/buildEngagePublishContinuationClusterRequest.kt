package com.google.android.googlevideodiscovery.common.engage.converters

import com.google.android.engage.common.datamodel.ContinuationCluster
import com.google.android.engage.service.PublishContinuationClusterRequest
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity

fun buildEngagePublishContinuationRequest(
    entities: List<ContinueWatchingEntity>,
    syncAcrossDevices: Boolean,
    accountProfile: AccountProfile,
    reason: PublishContinuationClusterReason
): PublishContinuationClusterRequest {
    val continuationClusterBuilder = ContinuationCluster.Builder()
    for (entity in entities) {
        continuationClusterBuilder.addEntity(entity.convertToEngageEntity())
    }
    continuationClusterBuilder.setSyncAcrossDevices(syncAcrossDevices)
    continuationClusterBuilder.setAccountProfile(accountProfile.toEngageAccountProfile())

    return PublishContinuationClusterRequest.Builder()
        .setContinuationCluster(continuationClusterBuilder.build())
        .build()
}

enum class PublishContinuationClusterReason(val message: String) {
    PROFILE_SELECTION("Profile was selected"),
    VIDEO_PAUSED("User paused playing video"),
    VIDEO_STOPPED("User stopped playing video"),
    ENTITY_REMOVED_FROM_CONTINUE_WATCHING_ROW("Entity was removed from the Continue Watching row"),
    NEXT_MOVIE_IN_SERIES("User completed watching a movie which is part of series and next movie should be in Continue Watching row"),
    NEXT_TV_EPISODE_IN_SERIES("User completed watching tv episode and next episode should be in Continue Watching row"),
}
