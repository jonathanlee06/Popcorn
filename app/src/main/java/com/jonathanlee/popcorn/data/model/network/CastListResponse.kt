package com.jonathanlee.popcorn.data.model.network

import com.jonathanlee.popcorn.data.model.Cast
import com.jonathanlee.popcorn.data.model.Crew

data class CastListResponse(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>
)