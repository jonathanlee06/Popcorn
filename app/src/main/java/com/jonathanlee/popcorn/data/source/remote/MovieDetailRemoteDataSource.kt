package com.jonathanlee.popcorn.data.source.remote

import com.jonathanlee.popcorn.data.model.network.ReviewListResponse
import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.data.source.task.MovieDetailTask
import retrofit2.Response

class MovieDetailRemoteDataSource : MovieDetailTask {
    private val movieTask = Api.provideMovieDetailTask()

    override suspend fun fetchVideos(id: Int): Response<VideoListResponse> {
        return movieTask.fetchVideos(id)
    }

    override suspend fun fetchReviews(id: Int): Response<ReviewListResponse> {
        return movieTask.fetchReviews(id)
    }
}