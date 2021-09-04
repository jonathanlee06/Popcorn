package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.network.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListTask {
    /**
     * [Movie Discover](https://developers.themoviedb.org/3/discover/movie-discover)
     *
     *  Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *  You can get a valid list of certifications from the  method.
     *
     *  @param [page] Specify the page of results to query.
     *
     *  @return [DiscoverMovieResponse] response
     */
    @GET("/3/discover/movie?language=en&sort_by=popularity.desc")
    suspend fun fetchMovie(@Query("page") page: Int): Response<MovieListResponse>
}