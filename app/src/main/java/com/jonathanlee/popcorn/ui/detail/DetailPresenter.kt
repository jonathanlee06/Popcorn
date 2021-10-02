package com.jonathanlee.popcorn.ui.detail

import com.jonathanlee.popcorn.data.source.Api

class DetailPresenter(private val view: DetailContract.View) : DetailContract.Presenter {

    init {
        view.presenter = this
    }

    override fun getBackdropImage(path: String?) {
        val fullPath = Api.getBackdropPath(path)
        view.setBackdropImage(fullPath)
    }
}