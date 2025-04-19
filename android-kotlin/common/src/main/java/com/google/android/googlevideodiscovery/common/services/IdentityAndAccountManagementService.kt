package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.fakes.createFakeProfile
import com.google.android.googlevideodiscovery.common.fakes.generateFakeAccount
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.toDbAccount
import com.google.android.googlevideodiscovery.common.models.toDbAccountProfile
import com.google.android.googlevideodiscovery.common.room.dto.toAccount
import com.google.android.googlevideodiscovery.common.room.dto.toAccountProfile
import com.google.android.googlevideodiscovery.common.room.repository.AccountProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IdentityAndAccountManagementService @Inject constructor(
    private val accountProfileRepository: AccountProfileRepository,
) {
    val loggedInAccount: Flow<Account?> = combine(
        accountProfileRepository.loggedInAccount,
        accountProfileRepository.accountProfiles
    ) { dbAccount, profiles ->
        dbAccount?.toAccount()?.let { account ->
            account.copy(
                profiles = profiles
                    .filter { it.accountId == account.id }
                    .map { it.toAccountProfile(account) }
            )
        }
    }

    private val _activeProfile = MutableStateFlow<AccountProfile?>(null)
    val activeProfile = _activeProfile.asStateFlow()

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

    suspend fun createProfile(account: Account, profileName: String): Result<Unit> {
        val profile = account.createFakeProfile(profileName)
        accountProfileRepository.createAccountProfile(profile.toDbAccountProfile())
        return Result.success(Unit)
    }

    suspend fun logoutAccounts() {
        accountProfileRepository.logoutAccounts()
        setActiveProfile(null)
    }

    fun setActiveProfile(profile: AccountProfile?) {
        _activeProfile.value = profile
    }
}