package com.jonathanlee.popcorn.data.model

sealed class TvItem {
    data class Item(val tvShow: Tv) : TvItem()
    object Footer : TvItem()
}

sealed class MovieItem {
    data class Item(val movie: Movie) : MovieItem()
    object Footer : MovieItem()
}
