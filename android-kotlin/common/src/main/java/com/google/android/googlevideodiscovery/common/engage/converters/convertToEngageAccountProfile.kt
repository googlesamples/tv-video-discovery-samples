package com.google.android.googlevideodiscovery.common.engage.converters

import com.google.android.googlevideodiscovery.common.models.AccountProfile

internal fun AccountProfile.convertToEngageAccountProfile() =
    com.google.android.engage.common.datamodel.AccountProfile.Builder()
        .setProfileId(id)
        .setAccountId(account.id)
        .build()