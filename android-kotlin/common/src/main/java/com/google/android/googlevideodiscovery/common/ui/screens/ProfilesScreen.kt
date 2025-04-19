package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.ui.foundation.Icon
import com.google.android.googlevideodiscovery.common.ui.foundation.MaterialTheme
import com.google.android.googlevideodiscovery.common.ui.foundation.Surface
import com.google.android.googlevideodiscovery.common.ui.foundation.Text

@Composable
internal fun ProfilesScreen(
    accountName: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome, $accountName!", fontSize = 24.sp)

        content()
    }
}

object ProfilesScreenDefaults {
    @Composable
    fun ProfilesGrid(
        profiles: List<AccountProfile>,
        onCreateProfile: () -> Unit,
        onSelectProfile: (AccountProfile) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
        ) {
            if (profiles.size < 3) {
                AccountProfileCard(
                    title = "Create profile",
                    onClick = onCreateProfile,
                    icon = Icons.Default.AddCircleOutline,
                )
            }

            for (profile in profiles) {
                AccountProfileCard(
                    title = profile.name,
                    onClick = { onSelectProfile(profile) },
                )
            }
        }
    }

    @Composable
    private fun AccountProfileCard(
        title: String,
        onClick: () -> Unit,
        icon: ImageVector = Icons.Default.AccountCircle,
    ) {
        Surface(
            onClick = onClick,
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
                Text(title, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
