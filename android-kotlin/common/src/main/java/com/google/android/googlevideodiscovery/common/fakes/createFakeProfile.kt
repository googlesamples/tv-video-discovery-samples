package com.google.android.googlevideodiscovery.common.fakes

import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile

fun Account.createFakeProfile(profileName: String): AccountProfile {
    val id = profileName.lowercase().replace(' ', '-')
    return AccountProfile(
        id = id,
        name = profileName,
        account = this
    )
}
