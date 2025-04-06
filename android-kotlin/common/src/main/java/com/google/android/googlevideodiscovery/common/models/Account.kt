package com.google.android.googlevideodiscovery.common.models

data class Account(
    val id: String,
    val name: String,
    val profiles: List<AccountProfile> = listOf()
)
