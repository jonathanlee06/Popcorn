package com.jonathanlee.popcorn.data.source

import com.jonathanlee.popcorn.data.source.task.CastTask
import com.jonathanlee.popcorn.data.source.task.DetailTask
import com.jonathanlee.popcorn.data.source.task.DiscoverTask
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private const val BASE_URL = "https://api.themoviedb.org/"
    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w500"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
    private const val BASE_BACKDROP_SMALL_PATH = "https://image.tmdb.org/t/p/w300"
    private const val BASE_CAST_PATH = "https://image.tmdb.org/t/p/w342"
    private const val BASE_IMDB_CAST_PATH = "https://www.imdb.com/name/"
    private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

    fun getPosterPath(posterPath: String?): String {
        return BASE_POSTER_PATH + posterPath
    }

    fun getBackdropPath(backdropPath: String?): String {
        return BASE_BACKDROP_PATH + backdropPath
    }

    fun getBackdropSmallPath(backdropPath: String?): String {
        return BASE_BACKDROP_SMALL_PATH + backdropPath
    }

    fun getCastPath(castPath: String?): String {
        return BASE_CAST_PATH + castPath
    }

    fun getImdbPath(castPath: String?): String {
        return BASE_IMDB_CAST_PATH + castPath
    }

    fun getYoutubeVideoPath(videoPath: String?): String {
        return YOUTUBE_VIDEO_URL + videoPath
    }

    fun getYoutubeThumbnailPath(thumbnailPath: String?): String {
        return "$YOUTUBE_THUMBNAIL_URL$thumbnailPath/default.jpg"
    }

    // Network Operations //

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()
    }

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideDetailTask(): DetailTask {
        val retrofit = provideRetrofit()
        return retrofit.create(DetailTask::class.java)
    }

    fun provideDiscoverTask(): DiscoverTask {
        val retrofit = provideRetrofit()
        return retrofit.create(DiscoverTask::class.java)
    }

    fun provideCastTask(): CastTask {
        val retrofit = provideRetrofit()
        return retrofit.create(CastTask::class.java)
    }
}

sealed class ApiResultState<out T> {
    object Loading : ApiResultState<Nothing>()
    data class Success<out T>(val data: T) : ApiResultState<T>()
    data class Failure(val exception: String?) : ApiResultState<Nothing>()
}