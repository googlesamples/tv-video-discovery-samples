package com.google.android.googlevideodiscovery.common.models

import com.google.android.googlevideodiscovery.common.room.dto.DbAccount

data class Account(
    val id: String,
    val name: String,
    val profiles: List<AccountProfile> = listOf(),
)

fun Account.toDbAccount(isLoggedIn: Boolean = true) = DbAccount(
    id = id,
    name = name,
    isLoggedIn = isLoggedIn
)