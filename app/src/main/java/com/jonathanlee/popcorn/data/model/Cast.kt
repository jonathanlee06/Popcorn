package com.jonathanlee.popcorn.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
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
    val profilePath: String?,
    @SerializedName("cast_id")
    val castId: Int,
    @SerializedName("character")
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    @SerializedName("order")
    val order: Int
) : Parcelable

data class CastDetail(
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("known_for_department") val knownForDepartment: String?,
    @SerializedName("deathday") val deathDay: String?,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("also_known_as") val alsoKnownAs: ArrayList<String>?,
    @SerializedName("gender") val gender: Int?,
    @SerializedName("biography") val biography: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("place_of_birth") val placeOfBirth: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("homepage") val homepage: String?
)

data class CastCredit(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: ArrayList<Int>,
    @SerializedName("id") val id: Int?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("character") val character: String?,
    @SerializedName("credit_id") val creditId: String?,
    @SerializedName("order") val order: Int?,
    @SerializedName("media_type") val mediaType: String?

)

data class CastPhoto(
    @SerializedName("aspect_ratio") var aspectRatio: Double?,
    @SerializedName("file_path") var filePath: String?,
    @SerializedName("height") var height: Int?,
    @SerializedName("iso_639_1") var iso6391: String?,
    @SerializedName("vote_average") var voteAverage: Double?,
    @SerializedName("vote_count") var voteCount: Int?,
    @SerializedName("width") var width: Int?
)