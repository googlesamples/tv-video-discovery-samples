package com.google.android.googlevideodiscovery.common.engage.converters

import com.google.android.engage.video.datamodel.WatchNextType
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingType

internal fun ContinueWatchingType.convertToEngageWatchNextType() = when (this) {
    ContinueWatchingType.NEXT -> WatchNextType.TYPE_NEXT
    ContinueWatchingType.CONTINUE -> WatchNextType.TYPE_CONTINUE
}
