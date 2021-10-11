package com.jonathanlee.popcorn.ui.main.movie

import com.jonathanlee.popcorn.data.model.MovieItem
import com.jonathanlee.popcorn.ui.base.BaseContract

interface MovieContract {
    interface View : BaseContract.View<Presenter> {
        fun onGetMovieListSuccess(page: Int, movies: List<MovieItem.Item>?)

        fun onGetMovieListFailure()

        fun addLoadMore()

        fun removeLoadMore()
    }

    interface Presenter : BaseContract.Presenter {
        fun getMovieList(page: Int)

        fun loadMore()

        fun resetPagination()
    }
}