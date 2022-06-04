package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CastTask {
    /**
     * [Cast Detail](https://developers.themoviedb.org/3/people/get-person-details)
     *
     * Get the primary person details by id.
     *
     * @param [id] Specify the id of cast.
     *
     * @return [VideoListResponse] response
     */
    @GET("/3/person/{person_id}")
    suspend fun fetchCastDetails(@Path("person_id") id: Int): Response<VideoListResponse>
}