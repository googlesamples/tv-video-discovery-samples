package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.room.dao.AccountProfileDao
import javax.inject.Inject

class SyncAcrossDevicesConsentService @Inject constructor(
    private val accountProfileDao: AccountProfileDao
) {
    suspend fun getSyncAcrossDevicesConsentValue(accountId: String): Boolean {
        val account = accountProfileDao.getAccount(accountId)
        return account?.userConsentToSendDataToGoogle == true
    }

    suspend fun updateSyncAcrossDevicesConsentValue(accountId: String, newConsentValue: Boolean) {
        val account = accountProfileDao.getAccount(accountId) ?: return
        val updatedAccount = account.copy(userConsentToSendDataToGoogle = newConsentValue)
        accountProfileDao.updateAccount(updatedAccount)
    }
}