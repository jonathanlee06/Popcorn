package com.jonathanlee.popcorn.data.model.network

import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.model.CrewCredit

data class CombinedCreditResponse(
    val id: Int?,
    val cast: ArrayList<CastCredit>,
    val crew: ArrayList<CrewCredit>
)