package com.jonathanlee.popcorn.ui.main.tv

import com.jonathanlee.popcorn.data.model.Tv
import com.jonathanlee.popcorn.ui.base.BaseContract

interface TvContract {
    interface View : BaseContract.View<Presenter> {
        fun onGetTvShowListSuccess(tvShows: ArrayList<Tv>)

        fun onGetTvShowListFailure()
    }

    interface Presenter : BaseContract.Presenter {
        fun getTvShowList()
    }
}