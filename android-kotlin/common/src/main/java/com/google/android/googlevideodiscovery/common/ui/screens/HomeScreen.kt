package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.ui.foundation.Button
import com.google.android.googlevideodiscovery.common.ui.foundation.Card
import com.google.android.googlevideodiscovery.common.ui.foundation.Icon
import com.google.android.googlevideodiscovery.common.ui.foundation.IconButton
import com.google.android.googlevideodiscovery.common.ui.foundation.ListItem
import com.google.android.googlevideodiscovery.common.ui.foundation.MaterialTheme
import com.google.android.googlevideodiscovery.common.ui.foundation.Text
import kotlin.time.Duration

@Composable
internal fun HomeScreen(
    activeProfile: AccountProfile,
    moviesContent: @Composable (rootHorizontalPadding: Dp) -> Unit,
    tvEpisodesContent: @Composable (rootHorizontalPadding: Dp) -> Unit,
    settingsDialogContent: @Composable (dismissDialog: () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
    continueWatchingContent: (@Composable (rootHorizontalPadding: Dp) -> Unit)? = null,
) {
    BoxWithConstraints {
        val rootHorizontalPadding = when {
            maxWidth <= 600.dp -> 20.dp
            maxWidth <= 800.dp -> 40.dp
            else -> 56.dp
        }
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(25.dp),
            contentPadding = PaddingValues(vertical = 30.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = rootHorizontalPadding),
                ) {
                    var showSettings by remember { mutableStateOf(false) }

                    IconButton(onClick = { showSettings = true }) {
                        Icon(Icons.Default.Settings, contentDescription = null)
                    }

                    if (showSettings) {
                        Dialog(onDismissRequest = { showSettings = false }) {
                            settingsDialogContent({ showSettings = false })
                        }
                    }
                }
            }
            item {
                Text(
                    "Hi, ${activeProfile.name}",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(horizontal = rootHorizontalPadding)
                )
            }

            continueWatchingContent?.let {
                item {
                    continueWatchingContent(rootHorizontalPadding)
                }
            }

            item {
                moviesContent(rootHorizontalPadding)
            }

            item {
                tvEpisodesContent(rootHorizontalPadding)
            }
        }
    }
}

internal object HomeScreenDefaults {
    @Composable
    fun ContinueWatchingChannel(
        rootHorizontalPadding: Dp,
        continueWatchingEntitiesCount: Int,
        cardContent: @Composable (Int) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Channel(
            rootHorizontalPadding = rootHorizontalPadding,
            title = "Continue Watching",
            itemCount = continueWatchingEntitiesCount,
            cardContent = cardContent,
            modifier = modifier,
        )
    }

    @Composable
    fun MoviesChannel(
        rootHorizontalPadding: Dp,
        movieCount: Int,
        cardContent: @Composable (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Channel(
            rootHorizontalPadding = rootHorizontalPadding,
            title = "Popular movies",
            itemCount = movieCount,
            cardContent = cardContent,
            modifier = modifier
        )
    }

    @Composable
    fun TvEpisodesChannel(
        rootHorizontalPadding: Dp,
        tvEpisodesCount: Int,
        cardContent: @Composable (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Channel(
            rootHorizontalPadding = rootHorizontalPadding,
            title = "Watch The Red Streak",
            itemCount = tvEpisodesCount,
            cardContent = cardContent,
            modifier = modifier
        )
    }

    @Composable
    fun ChannelCard(
        title: String,
        subtitle: String,
        onClick: () -> Unit,
        onLongClick: () -> Unit = {},
        cardContent: @Composable () -> Unit,
    ) {
        Column(
            modifier = Modifier.width(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9),
                onLongClick = onLongClick,
            ) {
                cardContent()
            }
            Text(
                title,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                ),
            )
            Text(
                subtitle,
                modifier = Modifier.padding(top = 5.dp),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )
            )
        }
    }

    @Composable
    fun ProgressBar(progressPercent: Float) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 5.dp)
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progressPercent)
                        .background(MaterialTheme.colorScheme.inverseSurface)
                )
            }
        }
    }

    @Composable
    fun RemoveContinueWatchingEntityConfirmationDialog(
        onDismiss: () -> Unit,
        onConfirm: () -> Unit,
        continueWatchingEntity: ContinueWatchingEntity,
    ) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        "Remove from Continue Watching?",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    )

                    Text(
                        "Remove '${continueWatchingEntity.entity.name}' will be removed from the Continue Watching row",
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
                        Button(onClick = onDismiss) {
                            Text("Cancel")
                        }
                        Button(onClick = onConfirm) {
                            Text("Remove")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SettingsContent(
        activeProfile: AccountProfile,
        currentUserConsentValue: Boolean,
        openProfileSelectionPage: () -> Unit,
        deleteCurrentProfile: () -> Unit,
        logout: () -> Unit,
        closeDialog: () -> Unit,
        updateUserConsentToShareDataWithGoogle: (newConsentValue: Boolean) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp)
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Settings", style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                )
            )

            ListItem(
                selected = currentUserConsentValue,
                onClick = { updateUserConsentToShareDataWithGoogle(!currentUserConsentValue) },
                leadingContent = {
                    val icon =
                        if (currentUserConsentValue) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text("Share data with Google?")
                },
                supportingContent = {
                    Text("Sharing your Continue Watching, Subscription information and our App recommendations for you with Google will improve your experience across all Google devices.")
                }
            )

            ListItem(
                selected = false,
                onClick = {
                    closeDialog()
                    openProfileSelectionPage()
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text("Change profile")
                },
            )

            ListItem(
                selected = false,
                onClick = {
                    closeDialog()
                    deleteCurrentProfile()
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.People,
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text("Delete profile - '${activeProfile.name}'")
                },
            )

            ListItem(
                selected = false,
                onClick = {
                    closeDialog()
                    logout()
                },
                leadingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null
                    )
                },
                headlineContent = {
                    Text("Logout")
                },
            )

            Button(onClick = closeDialog) {
                Text("Close")
            }
        }
    }

    fun buildEpisodeTitle(episodeName: String, episodeNumber: Int): String {
        return "E$episodeNumber: $episodeName"
    }

    fun buildSubtitle(releaseYear: Int, genre: String, duration: Duration): String {
        val hours = duration.inWholeHours
        val minutes = duration.inWholeMinutes % 60
        val formattedDuration = if (hours == 0L) "${minutes}m" else "${hours}h ${minutes}m"
        return "$releaseYear • $genre • $formattedDuration"
    }

    @Composable
    private fun Channel(
        rootHorizontalPadding: Dp,
        title: String,
        itemCount: Int,
        cardContent: @Composable (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 220.dp)
        ) {
            Text(
                title,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = rootHorizontalPadding)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(horizontal = rootHorizontalPadding)
            ) {
                items(itemCount) { index ->
                    cardContent(index)
                }
            }
        }
    }
}
