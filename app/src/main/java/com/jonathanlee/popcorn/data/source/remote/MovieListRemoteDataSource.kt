package com.jonathanlee.popcorn.data.source.remote

import com.jonathanlee.popcorn.data.model.network.MovieListResponse
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.data.source.task.MovieListTask
import retrofit2.Response

class MovieListRemoteDataSource : MovieListTask {
    private val movieTask = Api.provideMovieListTask()

    override suspend fun fetchMovie(page: Int): Response<MovieListResponse> {
        return movieTask.fetchMovie(page)
    }

}