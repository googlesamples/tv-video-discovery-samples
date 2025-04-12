package com.google.android.googlevideodiscovery.common.services.models

import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile

data class CreateNewProfileResult(
    val account: Account,
    val profile: AccountProfile
)
