package com.google.android.googlevideodiscovery.common.ui.navigation

import androidx.compose.runtime.Composable
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.Movie
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.models.TvEpisode
import com.google.android.googlevideodiscovery.common.models.toPlaybackEntity
import com.google.android.googlevideodiscovery.common.ui.screens.HomeScreenDefaults
import com.google.android.googlevideodiscovery.common.ui.screens.ProfilesScreenDefaults
import kotlin.time.Duration
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreen as CommonEntityScreen
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults
import com.google.android.googlevideodiscovery.common.viewmodels.PlaybackUpdateReason
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults.ProgressBar as EntityScreenProgressBar
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults.PauseButton as EntityScreenPauseButton
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults.PlayButton as EntityScreenPlayButton
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults.PlayerHeading as EntityScreenPlayerHeading
import com.google.android.googlevideodiscovery.common.ui.screens.HomeScreen as CommonHomeScreen
import com.google.android.googlevideodiscovery.common.ui.screens.LoginScreen as CommonLoginScreen
import com.google.android.googlevideodiscovery.common.ui.screens.ProfilesScreen as CommonProfilesScreen
import com.google.android.googlevideodiscovery.common.ui.screens.SettingsScreen as CommonSettingsScreen

class NavigationScreensImpl : NavigationScreens {
    @Composable
    override fun LoginScreen(
        performRegistration: () -> Unit,
        performLogin: () -> Unit
    ) {
        CommonLoginScreen(
            performRegistration = performRegistration,
            performLogin = performLogin
        )
    }

    @Composable
    override fun ProfilesScreen(
        account: Account,
        onCreateProfile: () -> Unit,
        onSelectProfile: (AccountProfile) -> Unit,
    ) {
        CommonProfilesScreen(accountName = account.name) {
            ProfilesScreenDefaults.ProfilesGrid(
                profiles = account.profiles,
                onCreateProfile = onCreateProfile,
                onSelectProfile = onSelectProfile,
            )
        }
    }

    @Composable
    override fun HomeScreen(
        activeProfile: AccountProfile,
        movies: List<Movie>,
        tvEpisodes: List<TvEpisode>,
        onEntityClick: (PlaybackEntity) -> Unit,
    ) {
        CommonHomeScreen(
            activeProfile = activeProfile,
            moviesContent = {
                HomeScreenDefaults.MoviesChannel(movieCount = movies.size, cardContent = { index ->
                    val movie = movies[index]
                    HomeScreenDefaults.ChannelCard(
                        title = movie.name,
                        subtitle = HomeScreenDefaults.buildSubtitle(
                            releaseYear = movie.releaseYear,
                            duration = movie.duration,
                            genre = movie.genre
                        ),
                        onClick = { onEntityClick(movie.toPlaybackEntity()) }
                    )
                })
            },
            tvEpisodesContent = {
                HomeScreenDefaults.TvEpisodesChannel(
                    tvEpisodesCount = tvEpisodes.size,
                    cardContent = { index ->
                        val tvEpisode = tvEpisodes[index]
                        HomeScreenDefaults.ChannelCard(
                            title = HomeScreenDefaults.buildEpisodeTitle(
                                episodeName = tvEpisode.name,
                                episodeNumber = tvEpisode.episodeNumber,
                            ),
                            subtitle = HomeScreenDefaults.buildSubtitle(
                                releaseYear = tvEpisode.releaseYear,
                                duration = tvEpisode.duration,
                                genre = tvEpisode.genre
                            ),
                            onClick = { onEntityClick(tvEpisode.toPlaybackEntity()) }
                        )
                    })
            }
        )
    }

    @Composable
    override fun EntityScreen(
        entity: PlaybackEntity?,
        isPlaying: Boolean,
        updateIsPlaying: (Boolean) -> Unit,
        onUpdatePlaybackPosition: (Duration, PlaybackUpdateReason) -> Unit
    ) {
        if (entity == null) {
            EntityScreenDefaults.LoadingEntityScreen()
            return
        }

        CommonEntityScreen(
            playPauseButtonContent = {
                if (isPlaying) {
                    EntityScreenPauseButton(onClick = { updateIsPlaying(false) })
                } else {
                    EntityScreenPlayButton(onClick = { updateIsPlaying(true) })
                }
            },
            playerTitleContent = {
                EntityScreenPlayerHeading(
                    entityTitle = entity.title,
                    entityReleaseYear = entity.releaseYear
                )
            },
            progressBarContent = {
                EntityScreenProgressBar(
                    isPlaying = isPlaying,
                    duration = entity.duration,
                    currentPosition = entity.playbackPosition,
                    onUpdatePlaybackPosition = onUpdatePlaybackPosition,
                    onPlaybackEnd = {
                        updateIsPlaying(false)
                    },
                )
            }
        )
    }

    @Composable
    override fun SettingsScreen() {
        CommonSettingsScreen()
    }
}