package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.fakes.FAKE_ACCOUNT
import com.google.android.googlevideodiscovery.common.fakes.createFakeProfile
import com.google.android.googlevideodiscovery.common.models.Account

class AuthService {
    suspend fun login(username: String, password: String): Result<Account> {
        // Login business logic
        return Result.success(FAKE_ACCOUNT)
    }

    suspend fun register(username: String, password: String): Result<Account> {
        // Registration business logic
        return Result.success(FAKE_ACCOUNT)
    }

    suspend fun createProfile(profileName: String): Result<Account> {
        // Profile creation business logic

        val profile = createFakeProfile()

        return Result.success(
            FAKE_ACCOUNT.copy(
                profiles = FAKE_ACCOUNT.profiles + profile
            )
        )
    }

    suspend fun deleteProfile(account: Account, profileId: String): Result<Account> {
        // Profile deletion business logic

        val profiles = account.profiles.filter { it.id != profileId }

        return Result.success(account.copy(profiles = profiles))
    }
}