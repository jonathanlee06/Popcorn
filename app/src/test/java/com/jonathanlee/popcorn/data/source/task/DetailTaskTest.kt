package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.network.CastListResponse
import com.jonathanlee.popcorn.data.model.network.GenreListResponse
import com.jonathanlee.popcorn.data.model.network.VideoListResponse
import com.jonathanlee.popcorn.data.source.ApiAbstract
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DetailTaskTest : ApiAbstract<DetailTask>() {

    private lateinit var task: DetailTask

    @Before
    fun setUp() {
        this.task = createTask(DetailTask::class.java)
    }

    @Test
    fun test_fetchMovieVideos() = runBlocking {
        enqueueResponse("/detail/fetchMovieVideos.json")
        val response = task.fetchVideos(634649)
        if (response.isSuccessful) {
            val result = response.body() as VideoListResponse
            assertTrue(result.results[0].key == "GfbNLLcrItI")
        }
    }

    @Test
    fun test_fetchTvShowsVideos() = runBlocking {
        enqueueResponse("/detail/fetchTvVideos.json")
        val response = task.fetchTvVideos(85552)
        if (response.isSuccessful) {
            val result = response.body() as VideoListResponse
            assertTrue(result.results[0].key == "JdwZwrs8Qt0")
        }
    }

    @Test
    fun test_fetchMovieGenres() = runBlocking {
        enqueueResponse("/detail/fetchMovieGenres.json")
        val response = task.fetchMovieGenres()
        if (response.isSuccessful) {
            val result = response.body() as GenreListResponse
            assertTrue(result.genres[0].id == 28)
            assertTrue(result.genres[0].name == "Action")
        }
    }

    @Test
    fun test_fetchTvGenres() = runBlocking {
        enqueueResponse("/detail/fetchTvGenres.json")
        val response = task.fetchTvGenres()
        if (response.isSuccessful) {
            val result = response.body() as GenreListResponse
            assertTrue(result.genres[0].id == 10759)
            assertTrue(result.genres[0].name == "Action & Adventure")
        }
    }

    @Test
    fun test_fetchMovieCast() = runBlocking {
        enqueueResponse("/detail/fetchMovieCast.json")
        val response = task.fetchMovieCast(634649)
        if (response.isSuccessful) {
            val result = response.body() as CastListResponse
            assertTrue(result.cast[0].id == 1136406)
            assertTrue(result.cast[0].name == "Tom Holland")
            assertTrue(result.crew[0].id == 2519)
            assertTrue(result.crew[0].name == "Sanja Milković Hays")
        }
    }

    @Test
    fun test_fetchTvCast() = runBlocking {
        enqueueResponse("/detail/fetchTvCast.json")
        val response = task.fetchTvCast(85552)
        if (response.isSuccessful) {
            val result = response.body() as CastListResponse
            assertTrue(result.cast[0].id == 505710)
            assertTrue(result.cast[0].name == "Zendaya")
            assertTrue(result.crew[0].id == 2484106)
            assertTrue(result.crew[0].name == "Ashley Levinson")
        }
    }
}