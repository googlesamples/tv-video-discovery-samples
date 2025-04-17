package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.models.Image
import com.google.android.googlevideodiscovery.common.models.MovieEntity
import com.google.android.googlevideodiscovery.common.models.PlatformSpecificUris
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class MoviesService @Inject constructor() {
    suspend fun fetchMovies(): List<MovieEntity> {
        // Business logic to fetch from server

        val ironGuy = createMovie(name = "Iron Guy", duration = 126.minutes, releaseYear = 2008)
        val ironGuy2 = createMovie(name = "Iron Guy 2", duration = 124.minutes, releaseYear = 2010)
        val ironGuy3 = createMovie(name = "Iron Guy 3", duration = 130.minutes, releaseYear = 2013)
        val bulk = createMovie(name = "Bulk", duration = 112.minutes, releaseYear = 2008)
        val hammerGuy =
            createMovie(name = "Thunder God", duration = 114.minutes, releaseYear = 2011)
        val americaCaptain =
            createMovie(name = "America Captain", duration = 136.minutes, releaseYear = 2011)
        val revengers = createMovie(name = "Revengers", duration = 143.minutes, releaseYear = 2012)
        val protectorsOfTheGalaxy =
            createMovie(
                name = "Protectors of the Galaxy",
                duration = 121.minutes,
                releaseYear = 2014
            )
        val protectorsOfTheGalaxy2 =
            createMovie(
                name = "Protectors of the Galaxy 2",
                duration = 136.minutes,
                releaseYear = 2017
            )
        val protectorsOfTheGalaxy3 =
            createMovie(
                name = "Protectors of the Galaxy 3",
                duration = 136.minutes,
                releaseYear = 2023
            )

        ironGuy.nextMovieEntity = ironGuy2
        ironGuy2.nextMovieEntity = ironGuy3
        protectorsOfTheGalaxy.nextMovieEntity = protectorsOfTheGalaxy2
        protectorsOfTheGalaxy2.nextMovieEntity = protectorsOfTheGalaxy3

        return listOf(
            ironGuy,
            ironGuy2,
            ironGuy3,
            bulk,
            hammerGuy,
            americaCaptain,
            revengers,
            protectorsOfTheGalaxy,
            protectorsOfTheGalaxy2,
            protectorsOfTheGalaxy3
        ).shuffled()
    }
}

private fun createMovie(name: String, duration: Duration, releaseYear: Int): MovieEntity {
    val slug = name.lowercase().replace(" ", "-")
    return MovieEntity(
        id = slug,
        name = name,
        images = listOf(
            Image(
                uri = "https://googletvvideodiscovery.com/$slug/hero.png",
                width = 320,
                height = 180
            )
        ),
        duration = duration,
        playbackUris = PlatformSpecificUris(
            mobileUri = "https://googletvvideodiscovery.com/slug/playbackUriForMobile",
            tvUri = "https://googletvvideodiscovery.com/slug/playbackUriForTv",
            iosUri = "https://googletvvideodiscovery.com/slug/playbackUriForIos",
        ),
        releaseYear = releaseYear,
        genre = "Sci-Fi",
        nextMovieEntity = null
    )
}
