package com.google.android.googlevideodiscovery.common.engage.converters

import androidx.core.net.toUri
import com.google.android.engage.common.datamodel.PlatformSpecificUri
import com.google.android.engage.common.datamodel.PlatformType

internal fun constructPlaybackUris(
    entityId: String,
    playbackPositionMillis: Long,
): List<PlatformSpecificUri> {
    // Your custom platform specific url construction logic
    val url =
        "googletv://videodiscovery.sample.app?entityId=$entityId&playbackPosition=$playbackPositionMillis".toUri()

    return listOf(
        PlatformSpecificUri.Builder()
            .setActionUri(url)
            .setPlatformType(PlatformType.TYPE_ANDROID_TV)
            .build(),
        PlatformSpecificUri.Builder()
            .setActionUri(url)
            .setPlatformType(PlatformType.TYPE_ANDROID_MOBILE)
            .build(),
        PlatformSpecificUri.Builder()
            .setActionUri(url)
            .setPlatformType(PlatformType.TYPE_IOS)
            .build()
    )
}
