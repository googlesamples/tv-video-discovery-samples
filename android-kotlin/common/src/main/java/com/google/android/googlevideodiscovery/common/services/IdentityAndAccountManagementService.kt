package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.fakes.createFakeProfile
import com.google.android.googlevideodiscovery.common.fakes.generateFakeAccount
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.toDbAccount
import com.google.android.googlevideodiscovery.common.models.toDbAccountProfile
import com.google.android.googlevideodiscovery.common.room.repository.AccountProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IdentityAndAccountManagementService @Inject constructor(
    private val accountProfileRepository: AccountProfileRepository
) {
    val loggedInAccount = accountProfileRepository.loggedInAccount
    val activeProfile = accountProfileRepository.activeProfile

    suspend fun getProfileById(profileId: String): AccountProfile? {
        return accountProfileRepository.getProfile(profileId)
    }

    suspend fun getAccountById(accountId: String): Account? {
        return accountProfileRepository.getAccount(accountId)
    }

    suspend fun login(username: String, password: String): Result<Unit> {
        val account = generateFakeAccount()
        accountProfileRepository.createAccount(account.toDbAccount())
        account.profiles.map { profile ->
            createProfile(account, profile.name)
        }
        accountProfileRepository.updateLoginStatus(account.id, isLoggedIn = true)
        return Result.success(Unit)
    }

    suspend fun register(username: String, password: String): Result<Unit> {
        val account = generateFakeAccount()
        accountProfileRepository.createAccount(account.toDbAccount())
        account.profiles.map { profile ->
            createProfile(account, profile.name)
        }
        accountProfileRepository.updateLoginStatus(account.id, isLoggedIn = true)
        return Result.success(Unit)
    }

    suspend fun createProfile(
        account: Account,
        profileName: String
    ): Result<Unit> {
        val profile = account.createFakeProfile(profileName)
        accountProfileRepository.createAccountProfile(profile.toDbAccountProfile())
        return Result.success(Unit)
    }

    fun setActiveProfile(profile: AccountProfile) {
        accountProfileRepository.setActiveProfile(profile)
    }
}