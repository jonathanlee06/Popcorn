package com.jonathanlee.popcorn.data.model.network

import com.google.gson.annotations.SerializedName
import com.jonathanlee.popcorn.data.model.CastCredit

data class SearchResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<SearchModel>,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
)

data class SearchModel(
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("popularity") val popularity: Float = 0F,
    @SerializedName("id") val id: Int = 0,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("profile_path") val profilePath: String? = null,
    @SerializedName("vote_average") val voteAverage: Float = 0F,
    @SerializedName("first_air_date") val firstAirDate: String? = null,
    @SerializedName("origin_country") val originCountry: List<String>,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String? = null,
    @SerializedName("vote_count") val voteCount: Int = 0,
    @SerializedName("name") val name: String? = null,
    @SerializedName("original_name") val originalName: String? = null,
    @SerializedName("media_type") val mediaType: String? = null,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean,
    @SerializedName("known_for") val knownFor: List<CastCredit>,
)