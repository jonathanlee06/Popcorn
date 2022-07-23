package com.jonathanlee.popcorn.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tv(
    var page: Int,
    var keywords: List<Keyword>? = emptyList(),
    var videos: List<Video>? = emptyList(),
    var reviews: List<Review>? = emptyList(),
    val poster_path: String?,
    val popularity: Float,
    val id: Int,
    val backdrop_path: String?,
    val vote_average: Float,
    val overview: String,
    val first_air_date: String?,
    val origin_country: List<String>,
    val genre_ids: List<Int>,
    val original_language: String,
    val vote_count: Int,
    val name: String,
    val original_name: String
) : Parcelable

data class TvDetails(
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("created_by")
    var createdBy: ArrayList<CreatedBy> = arrayListOf(),
    @SerializedName("episode_run_time")
    var episodeRunTime: ArrayList<Int> = arrayListOf(),
    @SerializedName("first_air_date")
    var firstAirDate: String? = null,
    @SerializedName("genres")
    var genres: ArrayList<Genre> = arrayListOf(),
    @SerializedName("homepage")
    var homepage: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("in_production")
    var inProduction: Boolean? = null,
    @SerializedName("languages")
    var languages: ArrayList<String> = arrayListOf(),
    @SerializedName("last_air_date")
    var lastAirDate: String? = null,
    @SerializedName("last_episode_to_air")
    var lastEpisodeToAir: LastEpisodeToAir? = LastEpisodeToAir(),
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("next_episode_to_air")
    var nextEpisodeToAir: NextEpisodeToAir? = null,
    @SerializedName("networks")
    var networks: ArrayList<Networks> = arrayListOf(),
    @SerializedName("number_of_episodes")
    var numberOfEpisodes: Int? = null,
    @SerializedName("number_of_seasons")
    var numberOfSeasons: Int? = null,
    @SerializedName("origin_country")
    var originCountry: ArrayList<String> = arrayListOf(),
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_name")
    var originalName: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("popularity")
    var popularity: Double? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("production_companies")
    var productionCompanies: ArrayList<ProductionCompanies> = arrayListOf(),
    @SerializedName("production_countries")
    var productionCountries: ArrayList<ProductionCountries> = arrayListOf(),
    @SerializedName("seasons")
    var seasons: ArrayList<Seasons> = arrayListOf(),
    @SerializedName("spoken_languages")
    var spokenLanguages: ArrayList<SpokenLanguages> = arrayListOf(),
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("tagline")
    var tagline: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null
)

data class CreatedBy(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("credit_id")
    var creditId: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("gender")
    var gender: Int? = null,
    @SerializedName("profile_path")
    var profilePath: String? = null
)

data class LastEpisodeToAir(
    @SerializedName("air_date")
    var airDate: String? = null,
    @SerializedName("episode_number")
    var episodeNumber: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("production_code")
    var productionCode: String? = null,
    @SerializedName("season_number")
    var seasonNumber: Int? = null,
    @SerializedName("still_path")
    var stillPath: String? = null,
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null
)

data class NextEpisodeToAir(
    @SerializedName("air_date")
    var airDate: String? = null,
    @SerializedName("episode_number")
    var episodeNumber: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("production_code")
    var productionCode: String? = null,
    @SerializedName("runtime")
    var runtime: Int? = null,
    @SerializedName("season_number")
    var seasonNumber: Int? = null,
    @SerializedName("still_path")
    var stillPath: String? = null,
    @SerializedName("vote_average")
    var voteAverage: Int? = null,
    @SerializedName("vote_count")
    var voteCount: Int? = null
)

data class Networks(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("logo_path")
    var logoPath: String? = null,
    @SerializedName("origin_country")
    var originCountry: String? = null
)

data class Seasons(
    @SerializedName("air_date")
    var airDate: String? = null,
    @SerializedName("episode_count")
    var episodeCount: Int? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("season_number")
    var seasonNumber: Int? = null
)

data class Classification(
    @SerializedName("iso_3166_1")
    val location: String? = null,
    val rating: String? = null
)