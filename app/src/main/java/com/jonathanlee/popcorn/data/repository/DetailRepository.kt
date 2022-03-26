package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.source.remote.DetailRemoteDataSource
import com.jonathanlee.popcorn.data.source.task.DetailTask

class DetailRepository private constructor(
    private val remoteDataSource: DetailRemoteDataSource = DetailRemoteDataSource()
) : DetailTask by remoteDataSource {

    companion object {
        private var INSTANCE: DetailRepository? = null

        fun getInstance(remoteDataSource: DetailRemoteDataSource): DetailRepository {
            return INSTANCE ?: DetailRepository(remoteDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: DetailRemoteDataSource): DetailRepository {
            INSTANCE = null
            return getInstance(remoteDataSource)
        }
    }
}