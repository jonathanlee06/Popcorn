package com.jonathanlee.popcorn.ui.detail

import com.jonathanlee.popcorn.data.model.Cast
import com.jonathanlee.popcorn.data.model.Details
import com.jonathanlee.popcorn.data.model.Video
import com.jonathanlee.popcorn.ui.base.BaseContract

interface DetailContract {
    interface View : BaseContract.View<Presenter> {
        fun setBackdropImage(path: String?)

        fun setBackdropPoster(path: String?)

        fun setGenres(genres: ArrayList<String>)

        fun setCasts(cast: ArrayList<Cast>)

        fun setVideos(video: ArrayList<Video>)

        fun setNoVideos()
    }

    interface Presenter : BaseContract.Presenter {
        fun getDetails(details: Details, entry: Int)

        fun getBackdropImage(path: String?)

        fun getBackdropPoster(path: String?)

        fun getGenres(id: List<Int>)

        fun getCasts(id: Int, entry: Int)

        fun getVideos(id: Int, entry: Int)
    }
}