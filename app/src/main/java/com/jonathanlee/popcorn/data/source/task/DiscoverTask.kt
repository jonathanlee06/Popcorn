package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.network.MovieListResponse
import com.jonathanlee.popcorn.data.model.network.TvShowListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverTask {
    /**
     * [Movie Discover](https://developers.themoviedb.org/3/discover/movie-discover)
     *
     *  Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *  You can get a valid list of certifications from the  method.
     *
     *  @param [page] Specify the page of results to query.
     *
     *  @return [MovieListResponse] response
     */
    @GET("/3/discover/movie?language=en&sort_by=popularity.desc&primary_release_year=2021")
    suspend fun fetchMovie(@Query("page") page: Int): Response<MovieListResponse>

    /**
     * [Tv Discover](https://developers.themoviedb.org/3/discover/tv-discover)
     *
     *  Discover TV shows by different types of data like average rating, number of votes, genres, the network they aired on and air dates.
     *
     *  @param [page] Specify the page of results to query.
     *
     *  @return [TvShowListResponse] response
     */
    @GET("/3/discover/tv?language=en&sort_by=popularity.desc&primary_release_year=2021")
    suspend fun fetchTvShow(@Query("page") page: Int): Response<TvShowListResponse>
}