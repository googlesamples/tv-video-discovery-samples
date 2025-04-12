package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.fakes.generateFakeAccount
import com.google.android.googlevideodiscovery.common.fakes.createFakeProfile
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.services.models.CreateNewProfileResult
import com.google.android.googlevideodiscovery.common.services.models.DeleteProfileResult
import javax.inject.Inject

class IdentityAndAccountManagementService @Inject constructor() {
    suspend fun login(username: String, password: String): Result<Account> {
        // Login business logic
        return Result.success(generateFakeAccount())
    }

    suspend fun register(username: String, password: String): Result<Account> {
        // Registration business logic
        return Result.success(generateFakeAccount())
    }

    suspend fun createProfile(
        account: Account,
        profileName: String
    ): Result<CreateNewProfileResult> {
        // Profile creation business logic

        val profile = account.createFakeProfile()

        val result = CreateNewProfileResult(
            profile = profile,
            account = account.copy(profiles = account.profiles + profile)
        )

        return Result.success(result)
    }

    suspend fun deleteProfile(profile: AccountProfile): Result<DeleteProfileResult> {
        // Profile deletion business logic

        val account = profile.account

        val result = DeleteProfileResult(
            account = account.copy(profiles = account.profiles.filter { it.id != profile.id })
        )

        return Result.success(result)
    }
}