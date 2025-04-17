package com.google.android.googlevideodiscovery.common.engage.converters

import com.google.android.engage.common.datamodel.AccountProfile as EngageAccountProfile
import com.google.android.engage.service.DeleteReason as EngageDeleteReason
import com.google.android.engage.service.DeleteClustersRequest

fun buildEngageDeleteClustersRequest(
    syncAcrossDevices: Boolean,
    accountProfile: EngageAccountProfile,
    deleteReason: DeleteReason
): DeleteClustersRequest {
    return DeleteClustersRequest.Builder()
        .setSyncAcrossDevices(syncAcrossDevices)
        .setAccountProfile(accountProfile)
        .setDeleteReason(deleteReason.engageDeleteReason)
        .build()
}

enum class DeleteReason(@EngageDeleteReason internal val engageDeleteReason: Int) {
    LOSS_OF_CONSENT(EngageDeleteReason.DELETE_REASON_LOSS_OF_CONSENT),
    USER_LOGOUT(EngageDeleteReason.DELETE_REASON_USER_LOG_OUT),
    ACCOUNT_DELETION(EngageDeleteReason.DELETE_REASON_ACCOUNT_DELETION),
    ACCOUNT_PROFILE_DELETION(EngageDeleteReason.DELETE_REASON_ACCOUNT_PROFILE_DELETION),
}