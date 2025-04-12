package com.google.android.googlevideodiscovery.common.services

import com.google.android.googlevideodiscovery.common.models.Image
import com.google.android.googlevideodiscovery.common.models.Movie
import com.google.android.googlevideodiscovery.common.models.PlatformSpecificUris
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

class MoviesService @Inject constructor() {
    suspend fun fetchMovies(): List<Movie> {
        // Business logic to fetch from server

        val ironGuy = createMovie("Iron Guy")
        val ironGuy2 = createMovie("Iron Guy 2")
        val ironGuy3 = createMovie("Iron Guy 3")
        val bulk = createMovie("Bulk")
        val hammerGuy = createMovie("Hammer Guy")
        val americaCaptain = createMovie("America Captain")
        val revengers = createMovie("Revengers")
        val protectorsOfTheGalaxy = createMovie("Protectors of the Galaxy")
        val protectorsOfTheGalaxy2 = createMovie("Protectors of the Galaxy 2")

        ironGuy.nextMovie = ironGuy2
        ironGuy2.nextMovie = ironGuy3
        protectorsOfTheGalaxy.nextMovie = protectorsOfTheGalaxy2


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
        )
}

private fun createMovie(name: String): Movie {
    val slug = name.lowercase().replace(" ", "-")
    return Movie(
        id = slug,
        name = name,
        images = listOf(
            Image(
                uri = "https://googletvvideodiscovery.com/$slug/hero.png",
                width = 320,
                height = 180
            )
        ),
        duration = (100..200).random().minutes,
        playbackUris = PlatformSpecificUris(
            mobileUri = "https://googletvvideodiscovery.com/slug/playbackUriForMobile",
            tvUri = "https://googletvvideodiscovery.com/slug/playbackUriForTv",
            iosUri = "https://googletvvideodiscovery.com/slug/playbackUriForIos",
        ),
        nextMovie = null
    )
}
