package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.source.remote.MovieDetailRemoteDataSource
import com.jonathanlee.popcorn.data.source.remote.MovieListRemoteDataSource

object Repository {
    fun provideMovieDetailRepository(): MovieDetailRepository {
        return MovieDetailRepository.getInstance(MovieDetailRemoteDataSource())
    }

    fun provideMovieListRepository(): MovieListRepository {
        return MovieListRepository.getInstance(MovieListRemoteDataSource())
    }
}