package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.network.SearchResponse
import com.jonathanlee.popcorn.data.source.ApiAbstract
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchTaskTest : ApiAbstract<SearchTask>() {

    private lateinit var task: SearchTask

    @Before
    fun setUp() {
        this.task = createTask(SearchTask::class.java)
    }

    @Test
    fun test_search() = runBlocking {
        enqueueResponse("/search/search.json")
        val response = task.search("John Wick")
        if (response.isSuccessful) {
            val result = response.body() as SearchResponse
            assertTrue(result.results.size == 17)
            assertTrue(result.results[0].id == 603692)
        }
    }

}