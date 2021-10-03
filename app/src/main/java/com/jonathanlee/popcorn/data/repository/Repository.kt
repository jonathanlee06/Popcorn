package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.source.remote.DiscoverRemoteDataSource
import com.jonathanlee.popcorn.data.source.remote.MovieDetailRemoteDataSource

object Repository {
    fun provideMovieDetailRepository(): MovieRepository {
        return MovieRepository.getInstance(MovieDetailRemoteDataSource())
    }

    fun provideDiscoverRepository(): DiscoverRepository {
        return DiscoverRepository.getInstance(DiscoverRemoteDataSource())
    }
}