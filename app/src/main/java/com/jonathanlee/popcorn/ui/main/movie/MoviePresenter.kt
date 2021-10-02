package com.jonathanlee.popcorn.ui.main.movie

import android.util.Log
import com.jonathanlee.popcorn.data.model.Movie
import com.jonathanlee.popcorn.data.model.network.MovieListResponse
import com.jonathanlee.popcorn.data.repository.DiscoverRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviePresenter(
    private val discoverRepository: DiscoverRepository,
    private val view: MovieContract.View,
    private val scope: CoroutineScope
) : MovieContract.Presenter {

    init {
        view.presenter = this
    }

    override fun getMovieList() {
        scope.launch(Dispatchers.IO) {
            try {
                val request = discoverRepository.fetchMovie(1)
                if (request.isSuccessful) {
                    val result = request.body() as MovieListResponse
                    val movieData = result.results as ArrayList<Movie>
                    Log.d("getMovieList", "getMovieList: response=${movieData[0].overview}")
                    withContext(Dispatchers.Main) {
                        view.onGetMovieListSuccess(movieData)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        view.onGetMovieListFailure()
                    }
                }
            } catch (e: Exception) {
                Log.e("getMovieList", "getMovieList: no internet, error=${e.message}")
                withContext(Dispatchers.Main) {
                    view.onGetMovieListFailure()
                }
            }
        }
    }

}