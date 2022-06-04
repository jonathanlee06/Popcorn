package com.jonathanlee.popcorn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Content(
    val contentDetails: ContentDetails,
    var videos: List<Video>? = ArrayList(),
    val vote: Vote
) : Parcelable

@Parcelize
data class ContentDetails(
    val contentId: Int = 0,
    val genreId: List<Int>,
    val backdropPath: String?,
    val posterPath: String?,
    val title: String,
    val tagline: String? = null,
    val releaseDate: String?,
    val summary: String
) : Parcelable

@Parcelize
data class Vote(
    val vote: Float? = null,
    val voteCount: String? = null
) : Parcelable