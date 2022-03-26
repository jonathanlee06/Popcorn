package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.source.remote.DiscoverRemoteDataSource
import com.jonathanlee.popcorn.data.source.task.DiscoverTask

class DiscoverRepository private constructor(
    private val remoteDataSource: DiscoverRemoteDataSource
) : DiscoverTask by remoteDataSource {

    companion object {
        private var INSTANCE: DiscoverRepository? = null

        fun getInstance(remoteDataSource: DiscoverRemoteDataSource): DiscoverRepository {
            return INSTANCE ?: DiscoverRepository(remoteDataSource)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        fun newInstance(remoteDataSource: DiscoverRemoteDataSource): DiscoverRepository {
            INSTANCE = null
            return getInstance(remoteDataSource)
        }
    }
}