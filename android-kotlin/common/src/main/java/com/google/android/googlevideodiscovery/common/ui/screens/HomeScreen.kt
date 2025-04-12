package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.ui.foundation.Text

@Composable
internal fun HomeScreen(activeProfile: AccountProfile) {
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 60.dp,
            horizontal = 40.dp
        )
    ) {
        item {
            Text("Hi, ${activeProfile.name}", fontSize = 48.sp)
        }
    }
}