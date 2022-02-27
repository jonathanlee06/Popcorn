package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.model.network.MovieListResponse
import com.jonathanlee.popcorn.data.model.network.TvShowListResponse
import com.jonathanlee.popcorn.data.source.remote.DiscoverRemoteDataSource
import com.jonathanlee.popcorn.data.source.task.DiscoverTask
import retrofit2.Response

class DiscoverRepository private constructor(
    private val remoteDataSource: DiscoverRemoteDataSource
) : DiscoverTask {

    companion object {
        private var INSTANCE: DiscoverRepository? = null

        fun getInstance(remoteDataSource: DiscoverRemoteDataSource): DiscoverRepository {
            return INSTANCE ?: DiscoverRepository(remoteDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: DiscoverRemoteDataSource): DiscoverRepository {
            INSTANCE = null
            return getInstance(remoteDataSource)
        }
    }

    override suspend fun fetchMovie(page: Int, sortBy: String): Response<MovieListResponse> {
        return remoteDataSource.fetchMovie(page, sortBy)
    }

    override suspend fun fetchTvShow(page: Int): Response<TvShowListResponse> {
        return remoteDataSource.fetchTvShow(page)
    }
}