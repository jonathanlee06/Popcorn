package com.jonathanlee.popcorn.data.source

import com.jonathanlee.popcorn.data.source.task.MovieDetailTask
import com.jonathanlee.popcorn.data.source.task.MovieListTask
import com.jonathanlee.popcorn.data.source.task.TvShowListTask
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    const val BASE_URL = "https://api.themoviedb.org/"
    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
    private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

    fun getPosterPath(posterPath: String?): String {
        return BASE_POSTER_PATH + posterPath
    }

    fun getBackdropPath(backdropPath: String?): String {
        return BASE_BACKDROP_PATH + backdropPath
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

    fun provideMovieDetailTask(): MovieDetailTask {
        val retrofit = provideRetrofit()
        return retrofit.create(MovieDetailTask::class.java)
    }

    fun provideMovieListTask(): MovieListTask {
        val retrofit = provideRetrofit()
        return retrofit.create(MovieListTask::class.java)
    }

    fun provideTvShowListTask(): TvShowListTask {
        val retrofit = provideRetrofit()
        return retrofit.create(TvShowListTask::class.java)
    }
}