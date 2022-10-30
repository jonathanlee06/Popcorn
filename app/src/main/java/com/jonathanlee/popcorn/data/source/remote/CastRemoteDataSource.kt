package com.jonathanlee.popcorn.data.source.remote

import com.jonathanlee.popcorn.data.model.CastDetail
import com.jonathanlee.popcorn.data.model.network.CastPhotoResponse
import com.jonathanlee.popcorn.data.model.network.CombinedCreditResponse
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.data.source.task.CastTask
import retrofit2.Response

class CastRemoteDataSource : CastTask {
    private val castTask = Api.provideCastTask()

    override suspend fun fetchCastDetails(id: Int): Response<CastDetail> {
        return castTask.fetchCastDetails(id)
    }

    override suspend fun fetchCastCredit(id: Int): Response<CombinedCreditResponse> {
        return castTask.fetchCastCredit(id)
    }

    override suspend fun fetchCastPhoto(id: Int): Response<CastPhotoResponse> {
        return castTask.fetchCastPhoto(id)
    }
}