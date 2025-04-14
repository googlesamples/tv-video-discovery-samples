package com.google.android.googlevideodiscovery.common.fakes

import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile

fun Account.createFakeProfile(profileName: String): AccountProfile {
    val accountId = id.lowercase().replace(' ', '-')
    val profileNameSlug = profileName.lowercase().replace(' ', '-')
    val profileId = "$accountId-$profileNameSlug"
    return AccountProfile(
        id = profileId,
        name = profileName,
        account = this
    )
}
