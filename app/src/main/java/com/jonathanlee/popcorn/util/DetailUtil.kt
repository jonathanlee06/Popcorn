package com.jonathanlee.popcorn.util

import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.model.Content
import com.jonathanlee.popcorn.data.model.ContentDetails
import com.jonathanlee.popcorn.data.model.Movie
import com.jonathanlee.popcorn.data.model.Tv
import com.jonathanlee.popcorn.data.model.Vote
import com.jonathanlee.popcorn.data.model.network.SearchModel

object DetailUtil {
    fun <T> parseToContent(content: T): Content {
        return when (content) {
            is Movie -> {
                Content(
                    contentDetails = ContentDetails(
                        contentId = content.id,
                        genreId = content.genre_ids,
                        backdropPath = content.backdrop_path,
                        posterPath = content.poster_path,
                        title = content.title,
                        releaseDate = content.release_date,
                        summary = content.overview
                    ),
                    videos = content.videos,
                    vote = Vote(
                        vote = content.vote_average,
                        voteCount = content.vote_count.toString()
                    )
                )
            }
            is Tv -> {
                Content(
                    contentDetails = ContentDetails(
                        contentId = content.id,
                        genreId = content.genre_ids,
                        backdropPath = content.backdrop_path,
                        posterPath = content.poster_path,
                        title = content.name,
                        releaseDate = content.first_air_date,
                        summary = content.overview
                    ),
                    videos = content.videos,
                    vote = Vote(
                        vote = content.vote_average,
                        voteCount = content.vote_count.toString()
                    )
                )
            }

            is SearchModel -> {
                val contentTitle = if (content.mediaType == "movie") content.title else content.name
                val title = contentTitle ?: ""
                Content(
                    contentDetails = ContentDetails(
                        contentId = content.id,
                        genreId = content.genreIds,
                        backdropPath = content.backdropPath,
                        posterPath = content.posterPath,
                        title = title,
                        releaseDate = content.firstAirDate,
                        summary = content.overview ?: ""
                    ),
                    videos = null,
                    vote = Vote(
                        vote = content.voteAverage,
                        voteCount = content.voteCount.toString()
                    )
                )
            }

            is CastCredit -> {
                val contentTitle = if (content.mediaType == "movie") content.title else content.name
                val title = contentTitle ?: ""
                Content(
                    contentDetails = ContentDetails(
                        contentId = content.id,
                        genreId = content.genreIds,
                        backdropPath = content.backdropPath,
                        posterPath = content.posterPath,
                        title = title,
                        releaseDate = content.firstAirDate,
                        summary = content.overview ?: ""
                    ),
                    videos = null,
                    vote = Vote(
                        vote = content.voteAverage.toFloat(),
                        voteCount = content.voteCount.toString()
                    )
                )
            }

            else -> throw Exception("unknown content type")
        }
    }
}