package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.network.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchTask {
    /**
     * [Search Multi](https://developers.themoviedb.org/3/search/multi-search)
     *
     *  Search multiple models in a single request. Multi search currently supports searching for movies, tv shows and people in a single request.
     *
     *  @param [query] Specify the search keyword.
     *  @param [page] Specify the page of results to query.
     *
     *  @return [SearchResponse] response
     */
    @GET("/3/search/multi")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): Response<SearchResponse>
}