package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.source.remote.CastRemoteDataSource
import com.jonathanlee.popcorn.data.source.task.CastTask

class CastRepository private constructor(
    private val remoteDataSource: CastRemoteDataSource = CastRemoteDataSource()
) : CastTask by remoteDataSource {

    companion object {
        private var INSTANCE: CastRepository? = null

        fun getInstance(remoteDataSource: CastRemoteDataSource): CastRepository {
            return INSTANCE ?: CastRepository(remoteDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: CastRemoteDataSource): CastRepository {
            INSTANCE = null
            return getInstance(remoteDataSource)
        }
    }
}