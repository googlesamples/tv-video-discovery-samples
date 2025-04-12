package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.ui.foundation.LocalFoundationsProvider

@Composable
fun ProfilesScreen(account: Account) {
    val foundations = LocalFoundationsProvider.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        foundations.Text("Welcome, ${account.name}!", style = TextStyle(fontSize = 24.sp))

        ProfilesGrid(profiles = account.profiles)
    }
}

@Composable
private fun ProfilesGrid(profiles: List<AccountProfile>) {
    val density = LocalDensity.current
    var screenWidth by remember { mutableStateOf(0.dp) }
    val cardsPerRow = remember(screenWidth) {
        when {
            screenWidth < 200.dp -> 1
            screenWidth < 300.dp -> 2
            screenWidth < 400.dp -> 3
            screenWidth < 600.dp -> 4
            else -> 5
        }
    }
    val profilesChunks = remember(cardsPerRow, profiles) {
        profiles.chunked(cardsPerRow)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged { size ->
                with(density) {
                    screenWidth = size.width.toDp()
                }
            }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 20.dp)
        ) {
            items(profilesChunks) { profilesChunk ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        20.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    for (profile in profilesChunk) {
                        AccountProfileCard(
                            profile = profile,
                            modifier = Modifier
                                .width(100.dp)
                                .border(1.dp, Color.Yellow),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AccountProfileCard(
    profile: AccountProfile,
    modifier: Modifier = Modifier
) {
    val foundations = LocalFoundationsProvider.current

    foundations.Surface(onClick = {}, modifier = modifier) {
        foundations.Text(profile.name)
    }
}
