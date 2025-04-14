package com.google.android.googlevideodiscovery.common.models

import com.google.android.googlevideodiscovery.common.room.dto.DbAccountProfile

data class AccountProfile(
    val id: String,
    val name: String,
    val account: Account
)

fun AccountProfile.toDbAccountProfile() = DbAccountProfile(
    id = id,
    name = name,
    accountId = account.id
)
