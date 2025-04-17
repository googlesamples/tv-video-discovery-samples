package com.google.android.googlevideodiscovery.common.room.repository

import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.room.dao.AccountProfileDao
import com.google.android.googlevideodiscovery.common.room.dto.DbAccount
import com.google.android.googlevideodiscovery.common.room.dto.DbAccountProfile
import com.google.android.googlevideodiscovery.common.room.dto.toAccount
import com.google.android.googlevideodiscovery.common.room.dto.toAccountProfile
import javax.inject.Inject

class AccountProfileRepository @Inject constructor(
    private val accountProfileDao: AccountProfileDao,
) {
    val loggedInAccount = accountProfileDao.getLoggedInAccount()
    val  accountProfiles = accountProfileDao.getAccountProfiles()

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