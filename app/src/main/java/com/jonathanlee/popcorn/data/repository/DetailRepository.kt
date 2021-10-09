package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.model.network.CastListResponse
import com.jonathanlee.popcorn.data.model.network.GenreListResponse
import com.jonathanlee.popcorn.data.model.network.ReviewListResponse
import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import com.jonathanlee.popcorn.data.source.remote.DetailRemoteDataSource
import com.jonathanlee.popcorn.data.source.task.DetailTask
import retrofit2.Response

class DetailRepository private constructor(
    private val remoteDataSource: DetailRemoteDataSource
) : DetailTask {

    companion object {
        private var INSTANCE: DetailRepository? = null

        fun getInstance(remoteDataSource: DetailRemoteDataSource): DetailRepository {
            return INSTANCE ?: DetailRepository(remoteDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: DetailRemoteDataSource): DetailRepository {
            INSTANCE = null
            return getInstance(remoteDataSource)
        }
    }

    override suspend fun fetchVideos(id: Int): Response<VideoListResponse> {
        return remoteDataSource.fetchVideos(id)
    }

    override suspend fun fetchTvVideos(id: Int): Response<VideoListResponse> {
        return remoteDataSource.fetchTvVideos(id)
    }

    override suspend fun fetchReviews(id: Int): Response<ReviewListResponse> {
        return remoteDataSource.fetchReviews(id)
    }

    override suspend fun fetchMovieGenres(): Response<GenreListResponse> {
        return remoteDataSource.fetchMovieGenres()
    }

    override suspend fun fetchTvGenres(): Response<GenreListResponse> {
        return remoteDataSource.fetchTvGenres()
    }

    override suspend fun fetchMovieCast(id: Int): Response<CastListResponse> {
        return remoteDataSource.fetchMovieCast(id)
    }

    override suspend fun fetchTvCast(id: Int): Response<CastListResponse> {
        return remoteDataSource.fetchTvCast(id)
    }
}