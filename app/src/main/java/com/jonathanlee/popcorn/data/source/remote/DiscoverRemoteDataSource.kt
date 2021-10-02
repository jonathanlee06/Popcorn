package com.jonathanlee.popcorn.data.source.remote

import com.jonathanlee.popcorn.data.model.network.MovieListResponse
import com.jonathanlee.popcorn.data.model.network.TvShowListResponse
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.data.source.task.DiscoverTask
import retrofit2.Response

class DiscoverRemoteDataSource : DiscoverTask {
    private val discoverTask = Api.provideDiscoverTask()

    override suspend fun fetchMovie(page: Int): Response<MovieListResponse> {
        return discoverTask.fetchMovie(page)
    }

    override suspend fun fetchTvShow(page: Int): Response<TvShowListResponse> {
        return discoverTask.fetchTvShow(page)
    }

}