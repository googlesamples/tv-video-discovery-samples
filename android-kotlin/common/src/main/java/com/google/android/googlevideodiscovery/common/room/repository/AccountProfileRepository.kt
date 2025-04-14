package com.google.android.googlevideodiscovery.common.room.repository

import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.room.dao.AccountProfileDao
import com.google.android.googlevideodiscovery.common.room.dto.DbAccount
import com.google.android.googlevideodiscovery.common.room.dto.DbAccountProfile
import com.google.android.googlevideodiscovery.common.room.dto.toAccount
import com.google.android.googlevideodiscovery.common.room.dto.toAccountProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AccountProfileRepository @Inject constructor(
    private val accountProfileDao: AccountProfileDao,
) {
    fun getLoggedInUser(): Flow<Account?> {
        return combine(
            accountProfileDao.getLoggedInUser(),
            accountProfileDao.getProfiles()
        ) { dbAccount, profiles ->
            dbAccount?.toAccount()?.let { account ->
                account.copy(
                    profiles = profiles.map { it.toAccountProfile(account) }
                )
            }
        }
    }

    suspend fun createAccount(account: DbAccount) {
        accountProfileDao.createAccount(account)
    }

    suspend fun createAccountProfile(profile: DbAccountProfile) {
        accountProfileDao.createAccountProfile(profile)
    }

    suspend fun updateLoginStatus(accountId: String, isLoggedIn: Boolean) {
        accountProfileDao.updateLoginStatus(accountId = accountId, isLoggedIn = isLoggedIn)
    }
}