package com.jonathanlee.popcorn.data.model

import com.google.gson.annotations.SerializedName

data class Crew(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String,
    @SerializedName("credit_id")
    val creditId: String,
    @SerializedName("department")
    val department: String,
    @SerializedName("job")
    val job: String
)

data class CrewCredit(
    @SerializedName("id") val id: Int?,
    @SerializedName("department") val department: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("episode_count") val episodeCount: Int?,
    @SerializedName("job") val job: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("origin_country") val originCountry: ArrayList<String>,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("media_type") val mediaType: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("genre_ids") val genreIds: ArrayList<Int>,
    @SerializedName("poster_path") val posterPath: String?

)