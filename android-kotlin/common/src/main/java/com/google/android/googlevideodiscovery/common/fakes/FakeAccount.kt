package com.google.android.googlevideodiscovery.common.fakes

import com.google.android.googlevideodiscovery.common.models.Account

private var fakeAccount: Account? = null

@Synchronized
fun generateFakeAccount(): Account {
    fakeAccount = fakeAccount ?: Account(
        id = "account-000001",
        name = "Champ",
    )

    val account = fakeAccount!!

    val profiles = listOf(
        account.createFakeProfile(FakeProfileNames.AANYA),
        account.createFakeProfile(FakeProfileNames.DIVYA),
    )

    return account.copy(profiles = profiles)
}
