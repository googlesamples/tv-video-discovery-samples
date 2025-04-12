package com.google.android.googlevideodiscovery.common.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.Movie
import com.google.android.googlevideodiscovery.common.models.TvEpisode
import com.google.android.googlevideodiscovery.common.ui.foundation.Text

@Composable
internal fun HomeScreen(
    activeProfile: AccountProfile,
    moviesContent: @Composable () -> Unit,
    tvEpisodesContent: @Composable () -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(
            vertical = 60.dp,
            horizontal = 40.dp
        )
    ) {
        item {
            Text("Hi, ${activeProfile.name}", fontSize = 48.sp)
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
    fun MoviesChannel(movies: List<Movie>) {
        Channel(title = "Popular movies", itemCount = movies.size) { index ->

        }
    }

    @Composable
    fun TvEpisodesChannel(tvEpisodes: List<TvEpisode>) {
        Channel(title = "Watch Star Battles", itemCount = tvEpisodes.size) { index ->

        }
    }

    @Composable
    private fun Channel(title: String, itemCount: Int, cardContent: @Composable (Int) -> Unit) {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Text(title)

            LazyRow {
                items(itemCount) { index ->
                    cardContent(index)
                }
            }
        }
    }
}
