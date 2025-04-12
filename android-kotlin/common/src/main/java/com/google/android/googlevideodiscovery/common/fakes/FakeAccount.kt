package com.google.android.googlevideodiscovery.common.fakes

import com.google.android.googlevideodiscovery.common.models.Account
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
val FAKE_ACCOUNT = Account(
    id = Uuid.random().toHexString(),
    name = "Champ",
    profiles = (1..2).map { createFakeProfile() },
)