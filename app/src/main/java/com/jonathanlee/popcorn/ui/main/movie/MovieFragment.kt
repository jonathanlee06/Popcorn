package com.jonathanlee.popcorn.ui.main.movie

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jonathanlee.bindingdelegate.ext.viewBinding
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Movie
import com.jonathanlee.popcorn.data.model.MovieItem
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.databinding.FragmentMovieBinding
import com.jonathanlee.popcorn.ui.base.BaseFragment
import com.jonathanlee.popcorn.ui.common.DialogHelper
import com.jonathanlee.popcorn.ui.common.option.OptionMenuDialogFragment
import com.jonathanlee.popcorn.ui.common.option.Options
import com.jonathanlee.popcorn.ui.detail.DetailActivity
import com.jonathanlee.popcorn.util.AdapterItemClickListener
import com.jonathanlee.popcorn.util.DetailUtil
import com.jonathanlee.popcorn.util.extension.navigateTo
import com.jonathanlee.popcorn.util.isNetworkConnected

class MovieFragment : BaseFragment(R.layout.fragment_movie), MovieContract.View {

    override lateinit var presenter: MovieContract.Presenter
    private lateinit var movieListAdapter: MovieAdapter
    private val binding by viewBinding(FragmentMovieBinding::bind)
    private var page: Int = 1
    private var listLayoutManager: GridLayoutManager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initPresenter()
    }

    override fun onGetMovieListSuccess(page: Int, movies: List<MovieItem.Item>?) {
        binding.apply {
            rvMovie.visibility = View.VISIBLE
            rlError.visibility = View.GONE
        }
        movieListAdapter.updateListData(page, movies)
        movieListAdapter.setOnItemClickListener(object : AdapterItemClickListener<Movie> {
            override fun onItemClicked(position: Int, model: Movie) {
                goToDetail(model)
            }
        })
        refreshController(
            srl = binding.srlMovie,
            isListOccupied = movieListAdapter.itemCount != 0,
            list = binding.rvMovie
        )
    }

    override fun onGetMovieListFailure(errorMsg: String?) {
        binding.apply {
            rvMovie.visibility = View.GONE
            rlError.visibility = View.VISIBLE
        }
        refreshController(
            srl = binding.srlMovie,
            isListOccupied = movieListAdapter.itemCount != 0,
            list = binding.rvMovie
        )
        if (!isNetworkConnected(requireContext())) {
            showNetworkErrorDialog(context = requireContext())
        } else {
            DialogHelper.showGenericErrorDialog(
                context = requireContext(),
                msg = errorMsg,
                positiveBtnCallback = { retry() }
            )
        }
    }

    override fun onLoadMoreFailed() {
        if (!isNetworkConnected(requireContext())) {
            showNetworkErrorDialog(context = requireContext())
        }
    }

    override fun addLoadMore() {
        movieListAdapter.apply {
            addLoadMore()
            binding.rvMovie.smoothScrollToPosition(this.itemCount - 1)
        }
    }

    override fun removeLoadMore() {
        movieListAdapter.removeLoadMore()
    }

    private fun goToDetail(model: Movie) {
        val details = DetailUtil.parseToContent(model)
        navigateTo(
            DetailActivity.getStartIntent(
                requireContext(),
                details,
                DetailActivity.ENTRY_FROM_MOVIE
            )
        )
    }

    private fun initPresenter() {
        presenter = MoviePresenter(
            Repository.provideDiscoverRepository(),
            this,
            lifecycleScope
        )
        presenter.getMovieList(page)
    }

    private fun initView() {
        listLayoutManager = GridLayoutManager(requireContext(), 2)
        movieListAdapter = MovieAdapter(listLayoutManager)
        binding.rvMovie.apply {
            layoutManager = listLayoutManager
            adapter = movieListAdapter
            setOnScrollChangeListener { _, _, _, _, _ ->
                if (listLayoutManager?.findLastCompletelyVisibleItemPosition() == (movieListAdapter.itemCount - 1)) {
                    presenter.loadMore()
                }
            }
        }
        binding.srlMovie.apply {
            setOnRefreshListener {
                overScrollController(
                    isListOccupied = movieListAdapter.itemCount != 0,
                    isFinishRefresh = false,
                    list = binding.rvMovie
                )
                presenter.getMovieList(page)
            }
            setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
        }
        binding.layoutFilterToolbar.apply {
            llViewStyle.setOnClickListener {
                val spanCount = listLayoutManager?.spanCount
                binding.rvMovie.apply {
                    listLayoutManager?.spanCount = if (spanCount == 2) 1 else 2
                    adapter = movieListAdapter.apply {
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

    private fun retry() {
        presenter.getMovieList(page)
    }
}