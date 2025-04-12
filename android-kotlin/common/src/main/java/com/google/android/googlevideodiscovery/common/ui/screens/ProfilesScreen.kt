package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.ui.foundation.Icon
import com.google.android.googlevideodiscovery.common.ui.foundation.Surface
import com.google.android.googlevideodiscovery.common.ui.foundation.Text

@Composable
fun ProfilesScreen(account: Account) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome, ${account.name}!", fontSize = 24.sp)

        ProfilesGrid(profiles = account.profiles)
    }
}

@Composable
private fun ProfilesGrid(profiles: List<AccountProfile>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 50.dp)
    ) {
        item {
            AccountProfileCard(
                icon = Icons.Default.AddCircleOutline,
                title = "Create profile"
            )
        }

        items(profiles) { profile ->
            AccountProfileCard(title = profile.name)
        }
    }
}

@Composable
private fun AccountProfileCard(
    title: String,
    icon: ImageVector = Icons.Default.AccountCircle,
) {
    Surface(
        onClick = {},
        modifier = Modifier
            .width(100.dp)
            .aspectRatio(3f / 4),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Text(title)
        }
    }
}