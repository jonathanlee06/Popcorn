package com.jonathanlee.popcorn.data.source.remote

import com.jonathanlee.popcorn.data.model.network.SearchResponse
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.data.source.task.SearchTask
import retrofit2.Response

class SearchRemoteDataSource : SearchTask {

    private val searchTask = Api.provideSearchTask()

    override suspend fun search(query: String, page: Int): Response<SearchResponse> {
        return searchTask.search(query, page)
    }
}