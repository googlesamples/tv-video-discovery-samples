package com.google.android.googlevideodiscovery.common.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.google.android.googlevideodiscovery.common.room.dto.DbAccount
import com.google.android.googlevideodiscovery.common.room.dto.DbAccountProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountProfileDao {

    @Query("SELECT * FROM accounts WHERE isLoggedIn = 1 LIMIT 1")
    fun getLoggedInUser(): Flow<DbAccount>

    @Query("SELECT * FROM account_profiles")
    fun getProfiles(): Flow<List<DbAccountProfile>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createAccount(account: DbAccount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAccountProfile(profile: DbAccountProfile)

    @Transaction
    suspend fun updateLoginStatus(accountId: String, isLoggedIn: Boolean) {
        if (isLoggedIn) {
            logoutAllUsers()
        } else {
            logoutUser(accountId)
        }
    }

    @Query("UPDATE accounts SET isLoggedIn = 0")
    suspend fun logoutAllUsers()

    @Query("UPDATE accounts SET isLoggedIn = 0 WHERE id = :accountId")
    suspend fun logoutUser(accountId: String)

}