package com.jonathanlee.popcorn.data.model.network

import com.jonathanlee.popcorn.data.model.Tv

data class TvShowListResponse(
    val page: Int,
    val results: List<Tv>,
    val total_results: Int,
    val total_pages: Int
)