package com.jonathanlee.popcorn.data.model.network

import com.jonathanlee.popcorn.data.model.Movie

data class MovieListResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
)
