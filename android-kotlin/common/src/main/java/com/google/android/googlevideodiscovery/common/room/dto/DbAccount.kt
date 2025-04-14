package com.google.android.googlevideodiscovery.common.room.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.googlevideodiscovery.common.models.Account

@Entity(tableName = "accounts")
data class DbAccount(
    @PrimaryKey val id: String,
    val name: String,
    val isLoggedIn: Boolean = false
)

fun DbAccount.toAccount() = Account(
    id = id,
    name = name,
    profiles = listOf()
)
