package com.jonathanlee.popcorn.data.model.network

import com.jonathanlee.popcorn.data.model.Genre

data class GenreListResponse(
    val genres: List<Genre>
)