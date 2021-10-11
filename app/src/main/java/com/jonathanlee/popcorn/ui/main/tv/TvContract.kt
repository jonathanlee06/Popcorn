package com.jonathanlee.popcorn.ui.main.tv

import com.jonathanlee.popcorn.data.model.TvItem
import com.jonathanlee.popcorn.ui.base.BaseContract

interface TvContract {
    interface View : BaseContract.View<Presenter> {
        fun onGetTvShowListSuccess(page: Int, tvShows: List<TvItem.Item>?)

        fun onGetTvShowListFailure()

        fun addLoadMore()

        fun removeLoadMore()
    }

    interface Presenter : BaseContract.Presenter {
        fun getTvShowList(page: Int)

        fun loadMore()

        fun resetPagination()
    }
}