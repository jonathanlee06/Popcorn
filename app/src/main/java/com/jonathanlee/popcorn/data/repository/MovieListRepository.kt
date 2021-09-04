package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.model.network.MovieListResponse
import com.jonathanlee.popcorn.data.source.remote.MovieListRemoteDataSource
import com.jonathanlee.popcorn.data.source.task.MovieListTask
import retrofit2.Response

class MovieListRepository private constructor(
    private val remoteDataSource: MovieListRemoteDataSource
) : MovieListTask {

    companion object {
        private var INSTANCE: MovieListRepository? = null

        fun getInstance(remoteDataSource: MovieListRemoteDataSource): MovieListRepository {
            return INSTANCE ?: MovieListRepository(remoteDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: MovieListRemoteDataSource): MovieListRepository {
            INSTANCE = null
            return getInstance(remoteDataSource)
        }
    }

    override suspend fun fetchMovie(page: Int): Response<MovieListResponse> {
        return remoteDataSource.fetchMovie(page)
    }
}