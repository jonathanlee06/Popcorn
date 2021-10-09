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

    private var page: Int = 1
    private var pageIsLoading: Boolean = false
    private var hasNextPage: Boolean = true
    private val maxResult: Int = 100
    private var currentResult: Int = 0

    init {
        view.presenter = this
    }

    override fun loadMore() {
        if (hasNextPage && !pageIsLoading) {
            view.addLoadMore()
            pageIsLoading = true
            page++
            getMovieList(page)
        }
    }

    override fun resetPagination() {
        hasNextPage = true
        pageIsLoading = false
        page = 1
        currentResult = 0
    }

    override fun getMovieList(page: Int) {
        scope.launch(Dispatchers.IO) {
            try {
                val request = discoverRepository.fetchMovie(page)
                if (request.isSuccessful) {
                    val result = request.body() as MovieListResponse
                    val movieData = result.results as ArrayList<Movie>
                    Log.d("getMovieList", "getMovieList: response=${movieData[0].overview}")
                    onQueryListSuccess(movieData)
                } else {
                    onQueryListFailed()
                }
            } catch (e: Exception) {
                Log.e("getMovieList", "getMovieList: no internet, error=${e.message}")
                withContext(Dispatchers.Main) {
                    view.onGetMovieListFailure()
                }
            }
        }
    }

    private suspend fun onQueryListSuccess(movieData: ArrayList<Movie>) {
        pageIsLoading = false
        currentResult += movieData.size
        hasNextPage = currentResult != maxResult
        Log.d("onQueryListSuccess", "currentCount=$currentResult")
        withContext(Dispatchers.Main) {
            view.removeLoadMore()
            view.onGetMovieListSuccess(page, movieData)
        }
    }

    private suspend fun onQueryListFailed() {
        pageIsLoading = false
        hasNextPage = false
        withContext(Dispatchers.Main) {
            view.removeLoadMore()
            view.onGetMovieListFailure()
        }
    }

}