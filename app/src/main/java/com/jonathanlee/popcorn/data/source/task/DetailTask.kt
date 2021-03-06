package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.TvDetails
import com.jonathanlee.popcorn.data.model.network.CastListResponse
import com.jonathanlee.popcorn.data.model.network.GenreListResponse
import com.jonathanlee.popcorn.data.model.network.TvShowClassification
import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailTask {
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
     * [TV Videos](https://developers.themoviedb.org/3/tv/get-tv-videos)
     *
     * Get the videos that have been added to a TV Show.
     *
     * @param [id] Specify the id of movie id.
     *
     * @return [VideoListResponse] response
     */
    @GET("/3/tv/{tv_id}/videos")
    suspend fun fetchTvVideos(@Path("tv_id") id: Int): Response<VideoListResponse>

    /**
     * [Movie Genre](https://developers.themoviedb.org/3/genres/get-movie-list)
     *
     * Get the genres for a movie.
     *
     * @return [GenreListResponse] response
     */
    @GET("/3/genre/movie/list")
    suspend fun fetchMovieGenres(): Response<GenreListResponse>

    /**
     * [Tv Genre](https://developers.themoviedb.org/3/genres/get-tv-list)
     *
     * Get the genres for a TV show.
     *
     * @return [GenreListResponse] response
     */
    @GET("/3/genre/tv/list")
    suspend fun fetchTvGenres(): Response<GenreListResponse>

    /**
     * [Movie Cast](https://developers.themoviedb.org/3/movies/get-movie-credits)
     *
     * Get the casts for a movie.
     *
     * @return [ReviewListResponse] response
     */
    @GET("/3/movie/{movie_id}/credits")
    suspend fun fetchMovieCast(@Path("movie_id") id: Int): Response<CastListResponse>

    /**
     * [Drama Cast](https://developers.themoviedb.org/3/tv/get-tv-credits)
     *
     * Get the casts for a movie.
     *
     * @return [ReviewListResponse] response
     */
    @GET("/3/tv/{tv_id}/credits")
    suspend fun fetchTvCast(@Path("tv_id") id: Int): Response<CastListResponse>

    /**
     * [Drama Details](https://developers.themoviedb.org/3/tv/get-tv-details)
     *
     * Get the details of a drama
     *
     * @return [TvDetails] response
     */
    @GET("/3/tv/{tv_id}")
    suspend fun fetchTvDetail(@Path("tv_id") id: Int): Response<TvDetails>

    /**
     * [Drama Content Ratings](https://developers.themoviedb.org/3/tv/get-tv-content-ratings)
     *
     * Get the classification ratings of a drama
     *
     * @return [TvShowClassification] response
     */
    @GET("/3/tv/{tv_id}/content_ratings")
    suspend fun fetchTvClassification(@Path("tv_id") id: Int): Response<TvShowClassification>
}