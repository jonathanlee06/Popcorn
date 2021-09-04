package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.model.network.ReviewListResponse
import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import com.jonathanlee.popcorn.data.source.remote.MovieDetailRemoteDataSource
import com.jonathanlee.popcorn.data.source.task.MovieDetailTask
import retrofit2.Response

class MovieDetailRepository private constructor(
    private val remoteDataSource: MovieDetailRemoteDataSource
) : MovieDetailTask {

    companion object {
        private var INSTANCE: MovieDetailRepository? = null

        fun getInstance(remoteDataSource: MovieDetailRemoteDataSource): MovieDetailRepository {
            return INSTANCE ?: MovieDetailRepository(remoteDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: MovieDetailRemoteDataSource): MovieDetailRepository {
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
}