package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.model.network.GenreListResponse
import com.jonathanlee.popcorn.data.model.network.ReviewListResponse
import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import com.jonathanlee.popcorn.data.source.remote.MovieDetailRemoteDataSource
import com.jonathanlee.popcorn.data.source.task.MovieDetailTask
import retrofit2.Response

class MovieRepository private constructor(
    private val remoteDataSource: MovieDetailRemoteDataSource
) : MovieDetailTask {

    companion object {
        private var INSTANCE: MovieRepository? = null

        fun getInstance(remoteDataSource: MovieDetailRemoteDataSource): MovieRepository {
            return INSTANCE ?: MovieRepository(remoteDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: MovieDetailRemoteDataSource): MovieRepository {
            INSTANCE = null
            return getInstance(remoteDataSource)
        }
    }

    override suspend fun fetchVideos(id: Int): Response<VideoListResponse> {
        return remoteDataSource.fetchVideos(id)
    }

    override suspend fun fetchReviews(id: Int): Response<ReviewListResponse> {
        return remoteDataSource.fetchReviews(id)
    }

    override suspend fun fetchMovieGenres(): Response<GenreListResponse> {
        return remoteDataSource.fetchMovieGenres()
    }
}