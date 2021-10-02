package com.jonathanlee.popcorn.ui.main.tv

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Details
import com.jonathanlee.popcorn.data.model.Tv
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.databinding.FragmentTvBinding
import com.jonathanlee.popcorn.ui.base.BaseFragment
import com.jonathanlee.popcorn.ui.detail.DetailActivity
import com.jonathanlee.popcorn.util.AdapterItemClickListener
import com.jonathanlee.popcorn.util.binding.viewBinding
import com.jonathanlee.popcorn.util.extension.navigateTo

class TvFragment : BaseFragment(), TvContract.View {
    override val layoutResId: Int = R.layout.fragment_tv
    override val binding: FragmentTvBinding by viewBinding()
    override lateinit var presenter: TvContract.Presenter
    private lateinit var tvShowListAdapter: TvAdapter
    private val tvShowList: ArrayList<Tv> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initPresenter()
    }

    override fun onGetTvShowListSuccess(tvShows: ArrayList<Tv>) {
        tvShowList.clear()
        tvShowList.addAll(tvShows)
        tvShowListAdapter.updateListData(tvShowList)
        tvShowListAdapter.setOnItemClickListener(object : AdapterItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                goToDetail(position)
            }
        })
        if (binding.srlTv.isRefreshing) {
            binding.srlTv.isRefreshing = false
            binding.rvTv.getChildAt(0).overScrollMode = View.OVER_SCROLL_ALWAYS
        }
    }

    override fun onGetTvShowListFailure() {
        //TODO("Not yet implemented")
    }

    private fun goToDetail(position: Int) {
        val tvAtPosition = tvShowList[position]
        val details = Details(
            backdropPath = tvAtPosition.backdrop_path,
            title = tvAtPosition.name,
            releaseDate = tvAtPosition.first_air_date,
            summary = tvAtPosition.overview,
            videos = tvAtPosition.videos
        )
        navigateTo(DetailActivity.getStartIntent(requireContext(), details))
    }

    private fun initPresenter() {
        presenter = TvPresenter(
            Repository.provideDiscoverRepository(),
            this,
            lifecycleScope
        )
        presenter.getTvShowList()
    }

    private fun initView() {
        tvShowListAdapter = TvAdapter()
        binding.rvTv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tvShowListAdapter
        }
        binding.srlTv.setOnRefreshListener {
            binding.rvTv.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            presenter.getTvShowList()
        }
    }
}