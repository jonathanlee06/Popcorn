package com.jonathanlee.popcorn.ui.main.tv

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jonathanlee.bindingdelegate.ext.viewBinding
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Tv
import com.jonathanlee.popcorn.data.model.TvItem
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.databinding.FragmentTvBinding
import com.jonathanlee.popcorn.ui.base.BaseFragment
import com.jonathanlee.popcorn.ui.common.option.OptionMenuDialogFragment
import com.jonathanlee.popcorn.ui.common.option.Options
import com.jonathanlee.popcorn.ui.detail.DetailActivity
import com.jonathanlee.popcorn.util.AdapterItemClickListener
import com.jonathanlee.popcorn.util.DetailUtil
import com.jonathanlee.popcorn.util.extension.navigateTo
import com.jonathanlee.popcorn.util.network.isNetworkConnected

class TvFragment : BaseFragment(R.layout.fragment_tv), TvContract.View {
    override lateinit var presenter: TvContract.Presenter
    private lateinit var tvShowListAdapter: TvAdapter
    private val binding by viewBinding(FragmentTvBinding::bind)
    private var listLayoutManager: GridLayoutManager? = null

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
        val details = DetailUtil.parseToContent(model)
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
        listLayoutManager = GridLayoutManager(requireContext(), 2)
        tvShowListAdapter = TvAdapter(listLayoutManager)
        binding.rvTv.apply {
            layoutManager = listLayoutManager
            adapter = tvShowListAdapter
            setOnScrollChangeListener { _, _, _, _, _ ->
                if (listLayoutManager?.findLastCompletelyVisibleItemPosition() == (tvShowListAdapter.itemCount - 1)) {
                    presenter.loadMore()
                }
            }
        }
        binding.srlTv.apply {
            setOnRefreshListener {
                overScrollController(
                    isListOccupied = tvShowListAdapter.itemCount != 0,
                    isFinishRefresh = false,
                    list = binding.rvTv
                )
                presenter.getTvShowList(1)
            }
            setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        }
        binding.layoutFilterToolbar.apply {
            llViewStyle.setOnClickListener {
                val spanCount = listLayoutManager?.spanCount
                binding.rvTv.apply {
                    listLayoutManager?.spanCount = if (spanCount == 2) 1 else 2
                    adapter = tvShowListAdapter.apply {
                        switchLayout(if (spanCount == 2) 1 else 0)
                        notifyItemRangeChanged(0, itemCount)
                    }
                }
                viewStyle.setBackgroundResource(getListIcon(listLayoutManager?.spanCount))
            }
            llSortBy.setOnClickListener {
                OptionMenuDialogFragment.show(
                    manager = mFragmentManager,
                    data = arrayListOf(
                        Options(
                            text = "Most Watched",
                            leftIcon = R.drawable.ic_list_view
                        ),
                        Options(
                            text = "Least Watched",
                            leftIcon = R.drawable.ic_grid_view
                        )
                    ),
                    stylingCallback = { binding ->
                        binding.tvTitle.text = "Sort"
                    }
                )
            }
        }
    }
}