package com.jonathanlee.popcorn.data.model.network

import com.jonathanlee.popcorn.data.model.Video

data class VideoListResponse(
    val id: Int,
    val results: List<Video>
)
