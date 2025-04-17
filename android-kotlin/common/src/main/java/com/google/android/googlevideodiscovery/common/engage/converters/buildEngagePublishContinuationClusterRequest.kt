package com.google.android.googlevideodiscovery.common.engage.converters

import com.google.android.engage.common.datamodel.ContinuationCluster
import com.google.android.engage.service.PublishContinuationClusterRequest
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity

fun buildEngagePublishContinuationRequest(
    entities: List<ContinueWatchingEntity>,
    syncAcrossDevices: Boolean,
    accountProfile: AccountProfile
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
