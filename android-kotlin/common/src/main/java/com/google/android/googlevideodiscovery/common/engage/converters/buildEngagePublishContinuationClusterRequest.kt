package com.google.android.googlevideodiscovery.common.engage.converters

import com.google.android.engage.common.datamodel.ContinuationCluster
import com.google.android.engage.service.PublishContinuationClusterRequest
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity

fun buildEngagePublishContinuationRequest(
    entities: List<ContinueWatchingEntity>,
    syncAcrossDevices: Boolean,
    accountProfile: AccountProfile,
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
    VIDEO_STOPPED("User stopped playing video"),
    ENTITY_REMOVED_FROM_CONTINUE_WATCHING_ROW("Entity was removed from the Continue Watching row"),
    USER_SCRUBBED_VIDEO("User scrubbed the video forwards/backwards")
}
