package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.network.MovieListResponse
import com.jonathanlee.popcorn.data.model.network.TvShowListResponse
import com.jonathanlee.popcorn.data.source.ApiAbstract
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DiscoverTaskTest : ApiAbstract<DiscoverTask>() {

    private lateinit var task: DiscoverTask

    @Before
    fun setUp() {
        this.task = createTask(DiscoverTask::class.java)
    }

    @Test
    fun test_fetchMovie() = runBlocking {
        enqueueResponse("/discover/fetchMovie.json")
        val response = task.fetchMovie(page = 1, sortBy = "popularity.desc")
        if (response.isSuccessful) {
            val result = response.body() as MovieListResponse
            assertTrue(result.results[0].id == 508947)
            assertTrue(result.total_pages == 32865)
            assertTrue(result.total_results == 657293)
        }
    }

    @Test
    fun test_fetchTvShow() = runBlocking {
        enqueueResponse("/discover/fetchTvShow.json")
        val response = task.fetchTvShow(page = 1)
        if (response.isSuccessful) {
            val result = response.body() as TvShowListResponse
            assertTrue(result.results[0].id == 114695)
            assertTrue(result.total_pages == 199)
            assertTrue(result.total_results == 3972)
        }
    }

}