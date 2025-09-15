package com.google.android.googlevideodiscovery.common.models

sealed interface RecommendationReason {

    /**
     * Represents a recommendation reason based on the user explicitly liking a specific title.
     *
     * @param titleName The name of the title that the user liked.
     */
    data class UserLikedTitle(val titleName: String) : RecommendationReason

    /**
     * Represents a recommendation reason because the item is a top performer on a partner platform.
     *
     * @param num The ranking number of the item on the partner platform (e.g., 1 for top 1).
     */
    data class ReasonTopOnPartner(val num: Int) : RecommendationReason

    /**
     * Represents a recommendation reason because there is no specific reason.
     *
     * @param reason A descriptive string explaining the generic reason.
     */
    data class Generic(val reason: String) : RecommendationReason

    /**
     * Represents a recommendation reason because the user has watched similar titles.
     *
     * @param similarTitles The list of titles that the user has watched.
     */
    data class WatchedSimilarTitles(val similarTitles: List<String>) :
        RecommendationReason

    /** Represents a recommendation reason because the item is from the user's watchlist. */
    data object FromUserWatchlist : RecommendationReason

    /** Represents a recommendation reason because the item is new on the partner platform. */
    data object NewOnPartner : RecommendationReason

    /** Represents a recommendation reason because the item is popular on the partner platform. */
    data object PopularOnPartner : RecommendationReason

    /** Represents a recommendation reason because the item is recommended for the user. */
    data object RecommendedForUser : RecommendationReason

    /** Represents a recommendation reason because user has watched the item before. */
    data object WatchAgain : RecommendationReason

}