package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.source.remote.CastRemoteDataSource
import com.jonathanlee.popcorn.data.source.remote.DetailRemoteDataSource
import com.jonathanlee.popcorn.data.source.remote.DiscoverRemoteDataSource

object Repository {
    fun provideMovieDetailRepository(): DetailRepository {
        return DetailRepository.getInstance(DetailRemoteDataSource())
    }

    fun provideDiscoverRepository(): DiscoverRepository {
        return DiscoverRepository.getInstance(DiscoverRemoteDataSource())
    }

    fun provideCastRepository(): CastRepository {
        return CastRepository.getInstance(CastRemoteDataSource())
    }
}