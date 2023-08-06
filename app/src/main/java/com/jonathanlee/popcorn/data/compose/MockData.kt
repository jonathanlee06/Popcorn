package com.jonathanlee.popcorn.data.compose

import com.jonathanlee.popcorn.data.model.Cast
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.data.model.network.SearchModel

val searchModel = SearchModel(
    id = 603692,
    originalLanguage = "en",
    originalTitle = "John Wick: Chapter 4",
    overview = "With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",
    popularity = 1698.338F,
    adult = false,
    backdropPath = "/h8gHn0OzBoaefsYseUByqsmEDMY.jpg",
    genreIds = listOf(28, 53, 80),
    posterPath = "/vZloFAK7NmvMGKE7VkF5UHaz0I.jpg",
    title = "John Wick: Chapter 4",
    video = false,
    voteAverage = 7.997F,
    voteCount = 1012,
    knownFor = emptyList(),
    originalName = null,
    originCountry = emptyList()
)

val searchList = listOf(searchModel, searchModel, searchModel)

val cast = Cast(
    adult = false,
    gender = 2,
    id = 345678,
    knownForDepartment = "department",
    name = "Cast Name",
    originalName = "Cast Original Name",
    popularity = 99.99,
    profilePath = "/4D0PpNI0kmP58hgrwGC3wCjxhnm.jpg",
    castId = 2222,
    character = "Character",
    creditId = "creditId",
    order = 2
)

private val cast_emptyProfile = Cast(
    adult = false,
    gender = 2,
    id = 345678,
    knownForDepartment = "department",
    name = "Cast Name",
    originalName = "Cast Original Name",
    popularity = 99.99,
    profilePath = null,
    castId = 2222,
    character = "Character",
    creditId = "creditId",
    order = 2
)

val castList = listOf(
    CastItem.Item(cast),
    CastItem.Item(cast_emptyProfile),
    CastItem.Item(cast),
    CastItem.Item(cast_emptyProfile)
)