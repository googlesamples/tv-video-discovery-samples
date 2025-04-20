package com.google.android.googlevideodiscovery.common.room.repository

import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.toDbAccount
import com.google.android.googlevideodiscovery.common.models.toDbAccountProfile
import com.google.android.googlevideodiscovery.common.room.dao.AccountProfileDao
import com.google.android.googlevideodiscovery.common.room.dto.toAccount
import com.google.android.googlevideodiscovery.common.room.dto.toAccountProfile
import javax.inject.Inject

class AccountProfileRepository @Inject constructor(
    private val accountProfileDao: AccountProfileDao,
) {
    val loggedInAccount = accountProfileDao.getLoggedInAccount()
    val accountProfiles = accountProfileDao.getAccountProfiles()

    suspend fun getAccount(accountId: String): Account? {
        return accountProfileDao.getAccount(accountId)?.toAccount()
    }

    suspend fun getProfile(profileId: String): AccountProfile? {
        val profile = accountProfileDao.getProfile(profileId)
        val accountId = profile?.accountId ?: return null
        val account = getAccount(accountId)
        return account?.let {
            profile.toAccountProfile(account)
        }
    }

    suspend fun createAccount(account: Account) {
        accountProfileDao.createAccount(account.toDbAccount())
    }

    suspend fun createAccountProfile(profile: AccountProfile) {
        accountProfileDao.createAccountProfile(profile.toDbAccountProfile())
    }

    suspend fun updateLoginStatus(accountId: String, isLoggedIn: Boolean) {
        accountProfileDao.updateLoginStatus(accountId = accountId, isLoggedIn = isLoggedIn)
    }

    suspend fun logoutAccounts() {
        accountProfileDao.logoutAccounts()
    }

    suspend fun deleteProfile(profile: AccountProfile) {
        accountProfileDao.deleteProfile(profile.toDbAccountProfile())
    }

    suspend fun deleteAccount(account: Account) {
        accountProfileDao.deleteAccount(account.toDbAccount())
    }
}