package com.jonathanlee.popcorn.ui.detail

import android.util.Log
import com.google.gson.Gson
import com.jonathanlee.popcorn.data.model.network.GenreListResponse
import com.jonathanlee.popcorn.data.repository.MovieRepository
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.util.SharedPreferencesUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPresenter(
    private val view: DetailContract.View,
    private val movieRepository: MovieRepository = Repository.provideMovieDetailRepository(),
    private val scope: CoroutineScope
) : DetailContract.Presenter {

    init {
        view.presenter = this
    }

    companion object {
        private const val MOVIE_GENRE_LIST = "movieGenre"
    }

    override fun getBackdropImage(path: String?) {
        val fullPath = Api.getBackdropPath(path)
        view.setBackdropImage(fullPath)
    }

    override fun getGenres(id: List<Int>) {
        if (checkIfCacheEmpty()) {
            scope.launch(Dispatchers.IO) {
                try {
                    val request = movieRepository.fetchMovieGenres()
                    if (request.isSuccessful) {
                        val result = request.body() as GenreListResponse
                        storeInCache(result)
                        val fromCache = getFromCache(id)
                        withContext(Dispatchers.Main) {
                            view.setGenres(fromCache)
                        }
                    } else {
                        Log.d("getGenres", "response error=${request.errorBody()}")
                    }
                } catch (e: Exception) {
                    Log.e("getGenres", "getGenres: error=${e.message}")
                }
            }
        } else {
            Log.d("getGenres", "getGenres from cache")
            view.setGenres(getFromCache(id))
        }
    }

    private fun checkIfCacheEmpty(): Boolean {
        val cache = SharedPreferencesUtils.getString(MOVIE_GENRE_LIST)
        return cache.isEmpty()
    }

    private fun storeInCache(result: GenreListResponse) {
        val toJson = Gson().toJson(result)
        SharedPreferencesUtils.putString(MOVIE_GENRE_LIST, toJson)
    }

    private fun getFromCache(genreId: List<Int>): ArrayList<String> {
        val genreList = SharedPreferencesUtils.getString(MOVIE_GENRE_LIST)
        val serialized = Gson().fromJson(genreList, GenreListResponse::class.java)
        Log.d("getFromCache", "getFromCache response=${serialized}")
        val mappedGenre: ArrayList<String> = ArrayList()
        genreId.forEach { fromList ->
            serialized.genres.forEach { fromCache ->
                if (fromList == fromCache.id) {
                    mappedGenre.add(fromCache.name)
                }
            }
        }
        return mappedGenre
    }
}