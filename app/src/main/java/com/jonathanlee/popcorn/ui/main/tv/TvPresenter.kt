package com.jonathanlee.popcorn.ui.main.tv

import android.util.Log
import com.jonathanlee.popcorn.data.model.Tv
import com.jonathanlee.popcorn.data.model.TvItem
import com.jonathanlee.popcorn.data.model.network.TvShowListResponse
import com.jonathanlee.popcorn.data.repository.DiscoverRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TvPresenter(
    private val discoverRepository: DiscoverRepository,
    private val view: TvContract.View,
    private val scope: CoroutineScope
) : TvContract.Presenter {

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
            getTvShowList(page)
        }
    }

    override fun resetPagination() {
        hasNextPage = true
        pageIsLoading = false
        page = 1
        currentResult = 0
    }

    override fun getTvShowList(page: Int) {
        scope.launch(Dispatchers.IO) {
            try {
                val request = discoverRepository.fetchTvShow(page)
                if (request.isSuccessful) {
                    val result = request.body() as TvShowListResponse
                    val tvShowData = result.results as ArrayList<Tv>
                    onQueryListSuccess(tvShowData)
                } else {
                    onQueryListFailure()
                }
            } catch (e: Exception) {
                Log.e("getTvShowList", "getTvShowList: no internet, error=${e.message}")
                withContext(Dispatchers.Main) {
                    view.onGetTvShowListFailure()
                }
            }
        }
    }

    private suspend fun onQueryListSuccess(tvShowData: ArrayList<Tv>) {
        pageIsLoading = false
        currentResult += tvShowData.size
        hasNextPage = currentResult != maxResult
        withContext(Dispatchers.Main) {
            view.removeLoadMore()
            view.onGetTvShowListSuccess(page = page, tvShows = toTvItemList(tvShowData))
        }
    }

    private suspend fun onQueryListFailure() {
        pageIsLoading = false
        hasNextPage = false
        withContext(Dispatchers.Main) {
            view.removeLoadMore()
            view.onGetTvShowListFailure()
        }
    }

    private fun toTvItemList(tvShowList: ArrayList<Tv>): List<TvItem.Item>? {
        val list: MutableList<TvItem.Item>?
        if (tvShowList.isNullOrEmpty()) {
            list = null
        } else {
            list = ArrayList()
            tvShowList.forEach { tv ->
                list.add(TvItem.Item(tv))
            }
        }
        return list
    }

}