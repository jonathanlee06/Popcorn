package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.network.GenreListResponse
import com.jonathanlee.popcorn.data.model.network.ReviewListResponse
import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailTask {
    /**
     * [Movie Videos](https://developers.themoviedb.org/3/movies/get-movie-videos)
     *
     * Get the videos that have been added to a movie.
     *
     * @param [id] Specify the id of movie id.
     *
     * @return [VideoListResponse] response
     */
    @GET("/3/movie/{movie_id}/videos")
    suspend fun fetchVideos(@Path("movie_id") id: Int): Response<VideoListResponse>

    /**
     * [Movie Reviews](https://developers.themoviedb.org/3/movies/get-movie-reviews)
     *
     * Get the user reviews for a movie.
     *
     * @param [id] Specify the id of movie id.
     *
     * @return [ReviewListResponse] response
     */
    @GET("/3/movie/{movie_id}/reviews")
    suspend fun fetchReviews(@Path("movie_id") id: Int): Response<ReviewListResponse>

    /**
     * [Movie Genre](https://developers.themoviedb.org/3/genres/get-movie-list)
     *
     * Get the genres for a movie.
     *
     * @return [ReviewListResponse] response
     */
    @GET("/3/genre/movie/list")
    suspend fun fetchMovieGenres(): Response<GenreListResponse>
}