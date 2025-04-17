package com.google.android.googlevideodiscovery.common.engage.converters

import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.engage.common.datamodel.AccountProfile as EngageAccountProfile

fun Account.toEngageAccountProfile(): EngageAccountProfile {
    return EngageAccountProfile.Builder()
        .setAccountId(id)
        .build()
}

internal fun AccountProfile.toEngageAccountProfile() =
    EngageAccountProfile.Builder()
        .setProfileId(id)
        .setAccountId(account.id)
        .build()
