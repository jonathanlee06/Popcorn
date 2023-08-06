package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.model.network.SearchModel
import com.jonathanlee.popcorn.data.model.network.SearchResponse
import com.jonathanlee.popcorn.data.repository.CastRepository.Companion.getInstance
import com.jonathanlee.popcorn.data.source.ApiResultState
import com.jonathanlee.popcorn.data.source.remote.SearchRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchRepository private constructor(
    private val remoteDataSource: SearchRemoteDataSource = SearchRemoteDataSource(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    companion object {
        private var INSTANCE: SearchRepository? = null

        fun getInstance(remoteDataSource: SearchRemoteDataSource): SearchRepository {
            return INSTANCE ?: SearchRepository(remoteDataSource).apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: SearchRemoteDataSource): SearchRepository {
            INSTANCE = null
            return getInstance(remoteDataSource)
        }
    }

    suspend fun search(query: String, page: Int = 1): Flow<ApiResultState<SearchResponse?>> {
        return flow {
            emit(ApiResultState.Loading)
            val response = remoteDataSource.search(query, page)
            if (response.isSuccessful) {
                val result = response.body()
                emit(ApiResultState.Success(if (result != null) filterSearch(result) else result))
            } else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(ApiResultState.Failure(errorMsg))
            }
        }.flowOn(dispatcher)
    }

    private fun filterSearch(response: SearchResponse): SearchResponse {
        fun hasNoImage(search: SearchModel): Boolean {
            return search.posterPath.isNullOrEmpty() && search.profilePath.isNullOrEmpty()
        }
        return SearchResponse(
            page = response.page,
            results = response.results.filter { !hasNoImage(it) },
            totalPages = response.totalPages,
            totalResults = response.totalResults
        )
    }
}