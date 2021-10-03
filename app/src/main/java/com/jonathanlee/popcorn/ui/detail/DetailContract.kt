package com.jonathanlee.popcorn.ui.detail

import com.jonathanlee.popcorn.ui.base.BaseContract

interface DetailContract {
    interface View : BaseContract.View<Presenter> {
        fun setBackdropImage(path: String?)

        fun setGenres(genres: ArrayList<String>)
    }

    interface Presenter : BaseContract.Presenter {
        fun getBackdropImage(path: String?)

        fun getGenres(id: List<Int>)
    }
}