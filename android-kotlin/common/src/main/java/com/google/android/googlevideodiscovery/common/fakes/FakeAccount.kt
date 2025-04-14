package com.google.android.googlevideodiscovery.common.fakes

import com.google.android.googlevideodiscovery.common.models.Account
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private var fakeAccount: Account? = null

@Synchronized
@OptIn(ExperimentalUuidApi::class)
fun generateFakeAccount(): Account {
    fakeAccount = fakeAccount ?: Account(
        id = Uuid.random().toHexString(),
        name = "Champ",
    )

    val account = fakeAccount!!

    val profiles = listOf(
        account.createFakeProfile(FakeProfileNames.AANYA),
        account.createFakeProfile(FakeProfileNames.DIVYA),
    )

    return account.copy(profiles = profiles)
}
