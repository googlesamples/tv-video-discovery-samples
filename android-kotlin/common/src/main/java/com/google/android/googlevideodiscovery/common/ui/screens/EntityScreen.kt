package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.runtime.Composable
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.ui.foundation.Text
import com.google.android.googlevideodiscovery.common.ui.utils.LockScreenOrientation
import com.google.android.googlevideodiscovery.common.ui.utils.ScreenOrientation

@Composable
internal fun EntityScreen(entity: PlaybackEntity?) {
    LockScreenOrientation(ScreenOrientation.LANDSCAPE)

    if (entity == null) {
        Text("Loading...")

        return
    }

    Text("Entity screen: ${entity.title}")
}