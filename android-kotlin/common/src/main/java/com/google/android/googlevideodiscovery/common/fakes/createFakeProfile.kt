package com.google.android.googlevideodiscovery.common.fakes

import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Account.createFakeProfile(profileName: String = getRandomName()): AccountProfile {
    return AccountProfile(
        id = Uuid.random().toHexString(),
        name = profileName,
        account = this
    )
}
