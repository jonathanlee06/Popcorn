package com.jonathanlee.popcorn.data.source.remote

import com.jonathanlee.popcorn.data.model.network.CastListResponse
import com.jonathanlee.popcorn.data.model.network.GenreListResponse
import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.data.source.task.DetailTask
import retrofit2.Response

class DetailRemoteDataSource : DetailTask {
    private val detailTask = Api.provideDetailTask()

    override suspend fun fetchVideos(id: Int): Response<VideoListResponse> {
        return detailTask.fetchVideos(id)
    }

    override suspend fun fetchTvVideos(id: Int): Response<VideoListResponse> {
        return detailTask.fetchTvVideos(id)
    }

    override suspend fun fetchMovieGenres(): Response<GenreListResponse> {
        return detailTask.fetchMovieGenres()
    }

    override suspend fun fetchTvGenres(): Response<GenreListResponse> {
        return detailTask.fetchTvGenres()
    }

    override suspend fun fetchMovieCast(id: Int): Response<CastListResponse> {
        return detailTask.fetchMovieCast(id)
    }

    override suspend fun fetchTvCast(id: Int): Response<CastListResponse> {
        return detailTask.fetchTvCast(id)
    }
}