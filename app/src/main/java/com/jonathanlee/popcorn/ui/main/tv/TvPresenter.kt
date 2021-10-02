package com.jonathanlee.popcorn.ui.main.tv

import com.jonathanlee.popcorn.data.model.Tv
import com.jonathanlee.popcorn.data.model.network.TvShowListResponse
import com.jonathanlee.popcorn.data.repository.TvShowListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TvPresenter(
    private val tvShowListRepository: TvShowListRepository,
    private val view: TvContract.View,
    private val scope: CoroutineScope
) : TvContract.Presenter {

    init {
        view.presenter = this
    }

    override fun getTvShowList() {
        scope.launch(Dispatchers.IO) {
            val request = tvShowListRepository.fetchTvShow(1)
            if (request.isSuccessful) {
                val result = request.body() as TvShowListResponse
                val tvShowData = result.results as ArrayList<Tv>
                withContext(Dispatchers.Main) {
                    view.onGetTvShowListSuccess(tvShowData)
                }
            }
        }
    }

}