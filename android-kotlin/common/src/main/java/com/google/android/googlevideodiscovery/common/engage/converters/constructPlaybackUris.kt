package com.google.android.googlevideodiscovery.common.engage.converters

import android.net.Uri
import androidx.core.net.toUri
import com.google.android.engage.common.datamodel.PlatformSpecificUri
import com.google.android.engage.common.datamodel.PlatformType
import com.google.android.googlevideodiscovery.common.models.PlatformSpecificUris

internal fun constructPlaybackUris(
    playbackUris: PlatformSpecificUris,
    profileId: String,
): List<PlatformSpecificUri> {
    return listOf(
        PlatformSpecificUri.Builder()
            .setActionUri(playbackUris.tvUri.toUri(profileId))
            .setPlatformType(PlatformType.TYPE_ANDROID_TV)
            .build(),
        PlatformSpecificUri.Builder()
            .setActionUri(playbackUris.mobileUri.toUri(profileId))
            .setPlatformType(PlatformType.TYPE_ANDROID_MOBILE)
            .build(),
        PlatformSpecificUri.Builder()
            .setActionUri(playbackUris.iosUri.toUri(profileId))
            .setPlatformType(PlatformType.TYPE_IOS)
            .build()
    )
}

private fun String.toUri(profileId: String): Uri =
    toUri().buildUpon().appendQueryParameter("profileId", profileId).build()
