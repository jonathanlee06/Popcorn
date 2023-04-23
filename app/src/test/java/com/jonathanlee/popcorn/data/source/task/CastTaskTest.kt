package com.jonathanlee.popcorn.data.source.task

import com.jonathanlee.popcorn.data.model.CastDetail
import com.jonathanlee.popcorn.data.model.network.CastPhotoResponse
import com.jonathanlee.popcorn.data.model.network.CombinedCreditResponse
import com.jonathanlee.popcorn.data.source.ApiAbstract
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CastTaskTest : ApiAbstract<CastTask>() {

    private lateinit var task: CastTask

    @Before
    fun setUp() {
        this.task = createTask(CastTask::class.java)
    }

    @Test
    fun test_fetchCastDetails() = runBlocking {
        enqueueResponse("/cast/fetchCastDetails.json")
        val response = task.fetchCastDetails(6384)
        if (response.isSuccessful) {
            val result = response.body() as CastDetail
            assertTrue(result.name == "Keanu Reeves")
            assertTrue(result.id == 6384)
        }
    }

    @Test
    fun test_fetchCastCredit() = runBlocking {
        enqueueResponse("/cast/fetchCastCredit.json")
        val response = task.fetchCastCredit(6384)
        if (response.isSuccessful) {
            val result = response.body() as CombinedCreditResponse
            assertTrue(result.cast.size == 3)
            assertTrue(result.id == 6384)
        }
    }

    @Test
    fun test_fetchCastPhoto() = runBlocking {
        enqueueResponse("/cast/fetchCastPhoto.json")
        val response = task.fetchCastPhoto(6384)
        if (response.isSuccessful) {
            val result = response.body() as CastPhotoResponse
            assertTrue(result.profiles?.size == 5)
            assertTrue(result.id == 6384)
        }
    }

}