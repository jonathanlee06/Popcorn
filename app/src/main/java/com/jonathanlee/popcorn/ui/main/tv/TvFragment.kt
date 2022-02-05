package com.jonathanlee.popcorn.ui.main.tv

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Details
import com.jonathanlee.popcorn.data.model.Tv
import com.jonathanlee.popcorn.data.model.TvItem
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.databinding.FragmentTvBinding
import com.jonathanlee.popcorn.ui.base.BaseFragment
import com.jonathanlee.popcorn.ui.detail.DetailActivity
import com.jonathanlee.popcorn.util.AdapterItemClickListener
import com.jonathanlee.popcorn.util.binding.viewBinding
import com.jonathanlee.popcorn.util.extension.navigateTo
import com.jonathanlee.popcorn.util.isNetworkConnected

class TvFragment : BaseFragment(), TvContract.View {
    override val layoutResId: Int = R.layout.fragment_tv
    override val binding: FragmentTvBinding by viewBinding()
    override lateinit var presenter: TvContract.Presenter
    private lateinit var tvShowListAdapter: TvAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initPresenter()
    }

    override fun addLoadMore() {
        tvShowListAdapter.apply {
            addLoadMore()
            binding.rvTv.smoothScrollToPosition(this.itemCount - 1)
        }
    }

    override fun removeLoadMore() {
        tvShowListAdapter.removeLoadMore()
    }

    override fun onGetTvShowListSuccess(page: Int, tvShows: List<TvItem.Item>?) {
        binding.apply {
            rvTv.visibility = View.VISIBLE
            rlError.visibility = View.GONE
        }
        tvShowListAdapter.updateListData(page, tvShows)
        tvShowListAdapter.setOnItemClickListener(object : AdapterItemClickListener<Tv> {
            override fun onItemClicked(position: Int, model: Tv) {
                goToDetail(model)
            }
        })
        refreshController(
            srl = binding.srlTv,
            isListOccupied = tvShowListAdapter.itemCount != 0,
            list = binding.rvTv
        )
    }

    override fun onGetTvShowListFailure() {
        binding.apply {
            rvTv.visibility = View.GONE
            rlError.visibility = View.VISIBLE
        }
        refreshController(
            srl = binding.srlTv,
            isListOccupied = tvShowListAdapter.itemCount != 0,
            list = binding.rvTv
        )
        if (!isNetworkConnected(requireContext())) {
            showNetworkErrorDialog(context = requireContext())
        }
    }

    override fun onLoadMoreFailed() {
        if (!isNetworkConnected(requireContext())) {
            showNetworkErrorDialog(context = requireContext())
        }
    }

    private fun goToDetail(model: Tv) {
        val details = Details(
            id = model.genre_ids,
            movieId = model.id,
            backdropPath = model.backdrop_path,
            posterPath = model.poster_path,
            title = model.name,
            releaseDate = model.first_air_date,
            summary = model.overview,
            videos = model.videos,
            isMovie = false
        )
        navigateTo(
            DetailActivity.getStartIntent(
                requireContext(),
                details,
                DetailActivity.ENTRY_FROM_TV
            )
        )
    }

    private fun initPresenter() {
        presenter = TvPresenter(
            Repository.provideDiscoverRepository(),
            this,
            lifecycleScope
        )
        presenter.getTvShowList(1)
    }

    private fun initView() {
        val gridLayout = GridLayoutManager(requireContext(), 2)
        tvShowListAdapter = TvAdapter(gridLayout)
        binding.rvTv.apply {
            layoutManager = gridLayout
            adapter = tvShowListAdapter
            setOnScrollChangeListener { v, _, _, _, _ ->
                if (gridLayout.findLastCompletelyVisibleItemPosition() == (tvShowListAdapter.itemCount - 1)) {
                    presenter.loadMore()
                    v.scrollTo(0, tvShowListAdapter.itemCount + 1)
                }
            }
        }
        binding.srlTv.setOnRefreshListener {
            overScrollController(
                isListOccupied = tvShowListAdapter.itemCount != 0,
                isFinishRefresh = false,
                list = binding.rvTv
            )
            presenter.getTvShowList(1)
        }
    }
}