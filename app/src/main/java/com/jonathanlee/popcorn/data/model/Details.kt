package com.jonathanlee.popcorn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Details(
    val id: List<Int>,
    val backdropPath: String?,
    val title: String,
    val tagline: String? = null,
    val releaseDate: String?,
    val summary: String,
    var videos: List<Video>? = ArrayList()
) : Parcelable