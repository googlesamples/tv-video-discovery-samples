package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.models.Image
import com.google.android.googlevideodiscovery.common.models.PlatformSpecificUris
import com.google.android.googlevideodiscovery.common.models.TvEpisode
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

class TvShowsService @Inject constructor() {
    suspend fun fetchTvEpisodes(): List<TvEpisode> {
        val episodes = (1..100).map {
            TvEpisode(
                id = "episode-$it",
                name = "Episode $it",
                episodeNumber = it,
                seasonNumber = 1,
                showTitle = "Star Battles",
                images = listOf(
                    Image(
                        uri = "https://googletvvideodiscovery.com/star-battles/episode-$it/hero.png",
                        width = 320,
                        height = 180
                    )
                ),
                duration = (40..50).random().minutes,
                playbackUris = PlatformSpecificUris(
                    mobileUri = "https://googletvvideodiscovery.com/star-battles/episode-$it/playbackUriForMobile",
                    tvUri = "https://googletvvideodiscovery.com/star-battles/episode-$it/playbackUriForTv",
                    iosUri = "https://googletvvideodiscovery.com/star-battles/episode-$it/playbackUriForIos",
                ),
                nextEpisode = null,
            )
        }

        for (i in episodes.indices) {
            val episode = episodes[i]
            val nextEpisode = episodes.getOrNull(i + 1)

            nextEpisode?.let {
                episode.nextEpisode = nextEpisode
            }
        }

        return episodes
    }
}