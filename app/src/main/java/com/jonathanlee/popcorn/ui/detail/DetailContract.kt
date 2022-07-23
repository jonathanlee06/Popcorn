package com.jonathanlee.popcorn.ui.detail

import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.data.model.ContentDetails
import com.jonathanlee.popcorn.data.model.Video
import com.jonathanlee.popcorn.ui.base.BaseContract

interface DetailContract {
    interface View : BaseContract.View<Presenter> {
        fun setBackdropImage(path: String?)

        fun setBackdropPoster(path: String?)

        fun setGenres(genres: ArrayList<String>)

        fun setCasts(cast: List<CastItem.Item>?)

        fun setVideos(video: ArrayList<Video>)

        fun setNoVideos()

        fun setContentProducer(name: String?)

        fun setContentClassification(classification: String?)
    }

    interface Presenter : BaseContract.Presenter {
        fun getDetails(contentDetails: ContentDetails, entry: Int)

        fun getBackdropImage(path: String?)

        fun getBackdropPoster(path: String?)

        fun getGenres(id: List<Int>)

        fun getCasts(id: Int, entry: Int)

        fun getVideos(id: Int, entry: Int)

        fun getContentProducer(id: Int, entry: Int)

        fun getContentClassification(id: Int, entry: Int)
    }
}