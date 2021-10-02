package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.model.network.TvShowListResponse
import com.jonathanlee.popcorn.data.repository.MovieListRepository.Companion.getInstance
import com.jonathanlee.popcorn.data.source.remote.TvShowListRemoteDataSource
import com.jonathanlee.popcorn.data.source.task.TvShowListTask
import retrofit2.Response

class TvShowListRepository private constructor(
    private val remoteDataSource: TvShowListRemoteDataSource
) : TvShowListTask {

    companion object {
        private var INSTANCE: TvShowListRepository? = null

        fun getInstance(remoteDataSource: TvShowListRemoteDataSource): TvShowListRepository {
            return INSTANCE ?: TvShowListRepository(remoteDataSource).apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: TvShowListRemoteDataSource): TvShowListRepository {
            INSTANCE = null
            return getInstance(remoteDataSource)
        }
    }

    override suspend fun fetchTvShow(page: Int): Response<TvShowListResponse> {
        return remoteDataSource.fetchTvShow(page)
    }

}