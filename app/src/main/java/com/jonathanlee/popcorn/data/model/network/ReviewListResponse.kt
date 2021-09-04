package com.jonathanlee.popcorn.data.model.network

import com.jonathanlee.popcorn.data.model.Review

data class ReviewListResponse(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val total_pages: Int,
    val total_results: Int
)
