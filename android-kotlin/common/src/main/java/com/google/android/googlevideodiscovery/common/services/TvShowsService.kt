package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.models.Image
import com.google.android.googlevideodiscovery.common.models.PlatformSpecificUris
import com.google.android.googlevideodiscovery.common.models.TvEpisodeEntity
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TvShowsService @Inject constructor() {
    suspend fun fetchTvEpisodes(): List<TvEpisodeEntity> {
        val episode1 = createTvEpisode(
            name = "Pilot",
            episodeNumber = 1,
            duration = 45.minutes
        )
        val episode2 = createTvEpisode(
            name = "Fastest Guy Alive",
            episodeNumber = 2,
            duration = 43.minutes
        )
        val episode3 = createTvEpisode(
            name = "Stuff You Can't Outrun",
            episodeNumber = 3,
            duration = 42.minutes
        )
        val episode4 = createTvEpisode(
            name = "Rogue Going",
            episodeNumber = 4,
            duration = 43.minutes
        )
        val episode5 = createTvEpisode(
            name = "Plastique",
            episodeNumber = 5,
            duration = 43.minutes
        )
        val episode6 = createTvEpisode(
            name = "The Red Streak Is Born",
            episodeNumber = 6,
            duration = 43.minutes
        )
        val episode7 = createTvEpisode(
            name = "Electricity Outage",
            episodeNumber = 7,
            duration = 43.minutes
        )
        val episode8 = createTvEpisode(
            name = "Red Streak vs Bow",
            episodeNumber = 8,
            duration = 43.minutes
        )
        val episode9 = createTvEpisode(
            name = "The Guy in the Yellow Suit",
            episodeNumber = 9,
            duration = 44.minutes
        )
        val episode10 = createTvEpisode(
            name = "Avenge of the Rogues",
            episodeNumber = 10,
            duration = 43.minutes
        )
        val episode11 = createTvEpisode(
            name = "The Audio and the Fury",
            episodeNumber = 11,
            duration = 43.minutes
        )
        val episode12 = createTvEpisode(
            name = "Mad for You",
            episodeNumber = 12,
            duration = 42.minutes
        )
        val episode13 = createTvEpisode(
            name = "The Nuclear Guy",
            episodeNumber = 13,
            duration = 43.minutes
        )
        val episode14 = createTvEpisode(
            name = "Outfall",
            episodeNumber = 14,
            duration = 43.minutes
        )
        val episode15 = createTvEpisode(
            name = "No Time",
            episodeNumber = 15,
            duration = 42.minutes
        )
        val episode16 = createTvEpisode(
            name = "Lia Snart",
            episodeNumber = 16,
            duration = 42.minutes
        )
        val episode17 = createTvEpisode(
            name = "Magician",
            episodeNumber = 17,
            duration = 43.minutes
        )
        val episode18 = createTvEpisode(
            name = "Worm-Eyed Bandit",
            episodeNumber = 18,
            duration = 42.minutes
        )
        val episode19 = createTvEpisode(
            name = "Every guy",
            episodeNumber = 19,
            duration = 43.minutes
        )
        val episode20 = createTvEpisode(
            name = "Trapped",
            episodeNumber = 20,
            duration = 43.minutes
        )
        val episode21 = createTvEpisode(
            name = "Grodd Survives",
            episodeNumber = 21,
            duration = 43.minutes
        )
        val episode22 = createTvEpisode(
            name = "Enemies",
            episodeNumber = 22,
            duration = 43.minutes
        )
        val episode23 = createTvEpisode(
            name = "Hole of Worm",
            episodeNumber = 23,
            duration = 45.minutes
        )

        episode1.nextEpisode = episode2
        episode2.nextEpisode = episode3
        episode3.nextEpisode = episode4
        episode4.nextEpisode = episode5
        episode5.nextEpisode = episode6
        episode6.nextEpisode = episode7
        episode7.nextEpisode = episode8
        episode8.nextEpisode = episode9
        episode9.nextEpisode = episode10
        episode10.nextEpisode = episode11
        episode11.nextEpisode = episode12
        episode12.nextEpisode = episode13
        episode13.nextEpisode = episode14
        episode14.nextEpisode = episode15
        episode15.nextEpisode = episode16
        episode16.nextEpisode = episode17
        episode17.nextEpisode = episode18
        episode18.nextEpisode = episode19
        episode19.nextEpisode = episode20
        episode20.nextEpisode = episode21
        episode21.nextEpisode = episode22
        episode22.nextEpisode = episode23

        return listOf(
            episode1,
            episode2,
            episode3,
            episode4,
            episode5,
            episode6,
            episode7,
            episode8,
            episode9,
            episode10,
            episode11,
            episode12,
            episode13,
            episode14,
            episode15,
            episode16,
            episode17,
            episode18,
            episode19,
            episode20,
            episode21,
            episode22,
            episode23
        )
    }
}

private fun createTvEpisode(
    name: String,
    episodeNumber: Int,
    duration: Duration,
): TvEpisodeEntity {
    val slug = name.lowercase().replace(" ", "-")
    return TvEpisodeEntity(
        name = name,
        id = slug,
        episodeNumber = episodeNumber,
        seasonNumber = 1,
        showTitle = "The Red Streak",
        images = listOf(
            Image(
                uri = "https://googletvvideodiscovery.com/the-red-streak/episode-$episodeNumber/hero.png",
                width = 320,
                height = 180
            )
        ),
        duration = duration,
        playbackUris = PlatformSpecificUris(
            mobileUri = "https://googletvvideodiscovery.com/the-red-streak/episode-$episodeNumber/playbackUriForMobile",
            tvUri = "https://googletvvideodiscovery.com/the-red-streak/episode-$episodeNumber/playbackUriForTv",
            iosUri = "https://googletvvideodiscovery.com/the-red-streak/episode-$episodeNumber/playbackUriForIos",
        ),
        releaseYear = 2014,
        genre = "Superhero",
        nextEpisode = null,
    )
}