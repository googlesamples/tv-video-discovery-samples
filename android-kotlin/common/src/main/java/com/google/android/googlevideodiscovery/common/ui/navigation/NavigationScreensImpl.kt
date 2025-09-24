package com.google.android.googlevideodiscovery.common.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.googlevideodiscovery.common.engage.converters.PublishContinuationClusterReason
import com.google.android.googlevideodiscovery.common.models.Account
import com.google.android.googlevideodiscovery.common.models.AccountProfile
import com.google.android.googlevideodiscovery.common.models.ContinueWatchingEntity
import com.google.android.googlevideodiscovery.common.models.MovieEntity
import com.google.android.googlevideodiscovery.common.models.PlaybackEntity
import com.google.android.googlevideodiscovery.common.models.TvEpisodeEntity
import com.google.android.googlevideodiscovery.common.models.VideoClipEntity
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults
import com.google.android.googlevideodiscovery.common.ui.screens.HomeScreenDefaults
import com.google.android.googlevideodiscovery.common.ui.screens.ProfilesScreenDefaults
import kotlin.time.Duration
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreen as CommonEntityScreen
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults.PauseButton as EntityScreenPauseButton
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults.PlayButton as EntityScreenPlayButton
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults.PlayerHeading as EntityScreenPlayerHeading
import com.google.android.googlevideodiscovery.common.ui.screens.EntityScreenDefaults.ProgressBar as EntityScreenProgressBar
import com.google.android.googlevideodiscovery.common.ui.screens.HomeScreen as CommonHomeScreen
import com.google.android.googlevideodiscovery.common.ui.screens.LoginScreen as CommonLoginScreen
import com.google.android.googlevideodiscovery.common.ui.screens.ProfilesScreen as CommonProfilesScreen

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
        deleteCurrentAccount: () -> Unit
    ) {
        CommonProfilesScreen(accountName = account.name, secondaryActions = {
            ProfilesScreenDefaults.DeleteAccountButton(
                onConfirmDelete = deleteCurrentAccount
            )
        }) {
            ProfilesScreenDefaults.ProfileList(
                profiles = account.profiles,
                onCreateProfile = onCreateProfile,
                onSelectProfile = onSelectProfile,
            )
        }
    }

    @Composable
    override fun HomeScreen(
        loggedInAccount: Account,
        activeProfile: AccountProfile,
        continueWatchingEntities: List<ContinueWatchingEntity>,
        movieEntities: List<MovieEntity>,
        tvEpisodeEntities: List<TvEpisodeEntity>,
        onConfirmRemoveFromContinueWatchingRow: (ContinueWatchingEntity) -> Unit,
        openProfileSelectionPage: () -> Unit,
        deleteCurrentProfile: () -> Unit,
        logout: () -> Unit,
        updateUserConsentToShareDataWithGoogle: (consentValue: Boolean) -> Unit,
        onEntityClick: (String) -> Unit,
    ) {
        CommonHomeScreen(
            activeProfile = activeProfile,
            continueWatchingContent = if (continueWatchingEntities.isEmpty()) {
                null
            } else {
                { rootHorizontalPadding ->
                    var confirmRemoveContinueWatchingEntity by remember {
                        mutableStateOf<ContinueWatchingEntity?>(null)
                    }

                    if (confirmRemoveContinueWatchingEntity != null) {
                        HomeScreenDefaults.RemoveContinueWatchingEntityConfirmationDialog(
                            continueWatchingEntity = confirmRemoveContinueWatchingEntity!!,
                            onDismiss = { confirmRemoveContinueWatchingEntity = null },
                            onConfirm = {
                                onConfirmRemoveFromContinueWatchingRow(
                                    confirmRemoveContinueWatchingEntity!!
                                )
                                confirmRemoveContinueWatchingEntity = null
                            },
                        )
                    }

                    HomeScreenDefaults.ContinueWatchingChannel(
                        rootHorizontalPadding = rootHorizontalPadding,
                        continueWatchingEntitiesCount = continueWatchingEntities.size,
                        cardContent = { index ->
                            val continueWatchingEntity = continueWatchingEntities[index]
                            val entity = continueWatchingEntity.entity
                            val cardTitle = when (entity) {
                                is MovieEntity -> entity.name
                                is TvEpisodeEntity -> HomeScreenDefaults.buildEpisodeTitle(
                                    episodeName = entity.name,
                                    episodeNumber = entity.episodeNumber,
                                )
                                is VideoClipEntity -> entity.name
                                else -> throw IllegalStateException("Unsupported video type for Continue Watching: $entity")
                            }

                            HomeScreenDefaults.ChannelCard(
                                title = cardTitle,
                                subtitle = HomeScreenDefaults.buildSubtitle(
                                    releaseYear = continueWatchingEntity.releaseYear,
                                    duration = continueWatchingEntity.duration,
                                    genre = continueWatchingEntity.genre
                                ),
                                onClick = { onEntityClick(entity.id) },
                                onLongClick = {
                                    confirmRemoveContinueWatchingEntity = continueWatchingEntity
                                }
                            ) {
                                HomeScreenDefaults.ProgressBar(progressPercent = continueWatchingEntity.progressPercent)
                            }
                        }
                    )
                }
            },
            moviesContent = { rootHorizontalPadding ->
                HomeScreenDefaults.MoviesChannel(
                    rootHorizontalPadding = rootHorizontalPadding,
                    movieCount = movieEntities.size,
                    cardContent = { index ->
                        val movie = movieEntities[index]
                        HomeScreenDefaults.ChannelCard(
                            title = movie.name,
                            subtitle = HomeScreenDefaults.buildSubtitle(
                                releaseYear = movie.releaseYear,
                                duration = movie.duration,
                                genre = movie.genre
                            ),
                            onClick = { onEntityClick(movie.id) }
                        ) {
                        }
                    })
            },
            tvEpisodesContent = { rootHorizontalPadding ->
                HomeScreenDefaults.TvEpisodesChannel(
                    rootHorizontalPadding = rootHorizontalPadding,
                    tvEpisodesCount = tvEpisodeEntities.size,
                    cardContent = { index ->
                        val tvEpisode = tvEpisodeEntities[index]
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
                            onClick = { onEntityClick(tvEpisode.id) }
                        ) {}
                    })
            },
            settingsDialogContent = { dismissDialog ->
                HomeScreenDefaults.SettingsContent(
                    activeProfile = activeProfile,
                    currentUserConsentValue = loggedInAccount.userConsentToSendDataToGoogle,
                    openProfileSelectionPage = openProfileSelectionPage,
                    deleteCurrentProfile = deleteCurrentProfile,
                    logout = logout,
                    closeDialog = dismissDialog,
                    updateUserConsentToShareDataWithGoogle = updateUserConsentToShareDataWithGoogle,
                )
            }
        )
    }

    @Composable
    override fun EntityScreen(
        entity: PlaybackEntity?,
        isPlaying: Boolean,
        updateIsPlaying: (Boolean) -> Unit,
        onUpdatePlaybackPosition: (Duration, PublishContinuationClusterReason?) -> Unit
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
            },
        )
    }
}