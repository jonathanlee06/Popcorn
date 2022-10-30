package com.jonathanlee.popcorn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class TvItem {
    data class Item(val tvShow: Tv) : TvItem()
    object Footer : TvItem()
}

sealed class MovieItem {
    data class Item(val movie: Movie) : MovieItem()
    object Footer : MovieItem()
}

sealed class CastItem : Parcelable {
    @Parcelize
    data class Item(val cast: Cast) : CastItem()
    @Parcelize
    object More : CastItem()
}
