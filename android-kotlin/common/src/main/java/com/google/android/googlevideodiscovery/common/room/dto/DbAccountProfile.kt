package com.google.android.googlevideodiscovery.common.room.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile

@Entity(
    tableName = "account_profiles", foreignKeys = [
        ForeignKey(
            entity = DbAccount::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DbAccountProfile(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(index = true) val accountId: String
)

fun DbAccountProfile.toAccountProfile(account: Account) = AccountProfile(
    id = id,
    name = name,
    account = account,
)
