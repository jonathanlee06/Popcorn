package com.jonathanlee.popcorn.data.model.network

import com.jonathanlee.popcorn.data.model.CastPhoto

data class CastPhotoResponse(
    val id: Int?,
    val profiles: ArrayList<CastPhoto>?
)