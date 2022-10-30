package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.CastDetail
import com.jonathanlee.popcorn.data.model.network.CastPhotoResponse
import com.jonathanlee.popcorn.data.model.network.CombinedCreditResponse
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
     * @return [CastDetail] response
     */
    @GET("/3/person/{person_id}")
    suspend fun fetchCastDetails(@Path("person_id") id: Int): Response<CastDetail>

    /**
     * [Cast Combined Credit](https://developers.themoviedb.org/3/people/get-person-combined-credits)
     *
     * Get the combined credit of persion by id.
     *
     * @param [id] Specify the id of cast.
     *
     * @return [CombinedCreditResponse] response
     */
    @GET("/3/person/{person_id}/combined_credits")
    suspend fun fetchCastCredit(@Path("person_id") id: Int): Response<CombinedCreditResponse>

    /**
     * [Cast Images](https://developers.themoviedb.org/3/people/get-person-images)
     *
     * Get the photos of persion by id.
     *
     * @param [id] Specify the id of cast.
     *
     * @return [CastPhotoResponse] response
     */
    @GET("/3/person/{person_id}/images")
    suspend fun fetchCastPhoto(@Path("person_id") id: Int): Response<CastPhotoResponse>
}