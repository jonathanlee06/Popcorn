package com.jonathanlee.popcorn.util

import com.jonathanlee.popcorn.data.model.*

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
            else -> throw Exception("unknown content type")
        }
    }
}