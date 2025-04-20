package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.ui.foundation.Button
import com.google.android.googlevideodiscovery.common.ui.foundation.Icon
import com.google.android.googlevideodiscovery.common.ui.foundation.MaterialTheme
import com.google.android.googlevideodiscovery.common.ui.foundation.Surface
import com.google.android.googlevideodiscovery.common.ui.foundation.Text

@Composable
internal fun ProfilesScreen(
    accountName: String,
    secondaryActions: @Composable () -> Unit,
    profilesContent: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome, $accountName!", fontSize = 24.sp)

            profilesContent()
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            secondaryActions()
        }
    }
}

object ProfilesScreenDefaults {
    @Composable
    fun ProfileList(
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
    fun DeleteAccountButton(
        onConfirmDelete: () -> Unit,
    ) {
        var openDeleteConfirmation by remember { mutableStateOf(false) }

        if (openDeleteConfirmation) {
            Dialog(onDismissRequest = { openDeleteConfirmation = false }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            "Delete account?",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        )

                        Text(
                            "Your account will be deleted and you will be logged out",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                15.dp, Alignment.End
                            )
                        ) {
                            Button(onClick = { openDeleteConfirmation = false }) {
                                Text("Cancel")
                            }
                            Button(
                                onClick = {
                                    openDeleteConfirmation = false
                                    onConfirmDelete()
                                }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }

        Button(onClick = { openDeleteConfirmation = true }) {
            Text("Delete account")
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
