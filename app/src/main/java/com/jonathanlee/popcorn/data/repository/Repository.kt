package com.jonathanlee.popcorn.data.repository

import com.jonathanlee.popcorn.data.source.remote.MovieDetailRemoteDataSource

object Repository {
    fun provideMovieDetailRepository(): MovieDetailRepository {
        return MovieDetailRepository.getInstance(MovieDetailRemoteDataSource())
    }
}