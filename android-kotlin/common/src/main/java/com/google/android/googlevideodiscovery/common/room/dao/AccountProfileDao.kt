package com.google.android.googlevideodiscovery.common.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.google.android.googlevideodiscovery.common.room.dto.DbAccount
import com.google.android.googlevideodiscovery.common.room.dto.DbAccountProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountProfileDao {

    @Query("SELECT * FROM accounts WHERE isLoggedIn = 1 LIMIT 1")
    fun getLoggedInAccount(): Flow<DbAccount?>

    @Query("SELECT * FROM account_profiles")
    fun getAccountProfiles(): Flow<List<DbAccountProfile>>

    @Query("SELECT * FROM account_profiles WHERE id = :profileId")
    suspend fun getProfile(profileId: String): DbAccountProfile?

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    suspend fun getAccount(accountId: String): DbAccount?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createAccount(account: DbAccount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAccountProfile(profile: DbAccountProfile)

    @Update
    suspend fun updateAccount(account: DbAccount)

    @Transaction
    suspend fun updateLoginStatus(accountId: String, isLoggedIn: Boolean) {
        logoutAccounts()
        if (isLoggedIn) {
            loginAccount(accountId = accountId)
        }
    }

    @Query("UPDATE accounts SET isLoggedIn = 0")
    suspend fun logoutAccounts()

    @Query("UPDATE accounts SET isLoggedIn = 1 WHERE id = :accountId")
    suspend fun loginAccount(accountId: String)

}