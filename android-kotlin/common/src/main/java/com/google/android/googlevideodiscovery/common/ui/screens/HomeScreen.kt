package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.ui.foundation.Card
import com.google.android.googlevideodiscovery.common.ui.foundation.MaterialTheme
import com.google.android.googlevideodiscovery.common.ui.foundation.Text
import kotlin.time.Duration

@Composable
internal fun HomeScreen(
    activeProfile: AccountProfile,
    moviesContent: @Composable () -> Unit,
    tvEpisodesContent: @Composable () -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        contentPadding = PaddingValues(vertical = 30.dp)
    ) {
        item {
            Text(
                "Hi, ${activeProfile.name}",
                fontSize = 48.sp,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
        }

        item {
            moviesContent()
        }

        item {
            tvEpisodesContent()
        }
    }
}

internal object HomeScreenDefaults {
    @Composable
    fun MoviesChannel(
        movieCount: Int,
        cardContent: @Composable (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Channel(
            title = "Popular movies",
            itemCount = movieCount,
            cardContent = cardContent,
            modifier = modifier
        )
    }

    @Composable
    fun TvEpisodesChannel(
        tvEpisodesCount: Int,
        cardContent: @Composable (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Channel(
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
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                onClick = {},
                modifier = Modifier
                    .width(150.dp)
                    .aspectRatio(16f / 9),
            ) {
            }
            Text(
                title,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                subtitle,
                modifier = Modifier.padding(top = 5.dp),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodySmall
            )
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
        title: String,
        itemCount: Int,
        cardContent: @Composable (Int) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            Text(title, fontSize = 24.sp, modifier = Modifier.padding(horizontal = 40.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(horizontal = 40.dp)
            ) {
                items(itemCount) { index ->
                    cardContent(index)
                }
            }
        }
    }
}
