package com.jonathanlee.popcorn.data.source.remote

import com.jonathanlee.popcorn.data.model.network.TvShowListResponse
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.data.source.task.TvShowListTask
import retrofit2.Response

class TvShowListRemoteDataSource : TvShowListTask {
    private val tvShowTask = Api.provideTvShowListTask()

    override suspend fun fetchTvShow(page: Int): Response<TvShowListResponse> {
        return tvShowTask.fetchTvShow(page)
    }
}