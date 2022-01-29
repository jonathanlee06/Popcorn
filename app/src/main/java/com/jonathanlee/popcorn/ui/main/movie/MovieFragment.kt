package com.jonathanlee.popcorn.ui.main.movie

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Details
import com.jonathanlee.popcorn.data.model.Movie
import com.jonathanlee.popcorn.data.model.MovieItem
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.databinding.FragmentMovieBinding
import com.jonathanlee.popcorn.ui.base.BaseFragment
import com.jonathanlee.popcorn.ui.detail.DetailActivity
import com.jonathanlee.popcorn.util.AdapterItemClickListener
import com.jonathanlee.popcorn.util.binding.viewBinding
import com.jonathanlee.popcorn.util.extension.navigateTo

class MovieFragment : BaseFragment(), MovieContract.View {

    override val layoutResId: Int = R.layout.fragment_movie
    override val binding: FragmentMovieBinding by viewBinding()
    override lateinit var presenter: MovieContract.Presenter
    private lateinit var movieListAdapter: MovieAdapter
    private var page: Int = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initPresenter()
    }

    override fun onGetMovieListSuccess(page: Int, movies: List<MovieItem.Item>?) {
        binding.apply {
            srlMovie.visibility = View.VISIBLE
            rlError.visibility = View.GONE
        }
        movieListAdapter.updateListData(page, movies)
        movieListAdapter.setOnItemClickListener(object : AdapterItemClickListener<Movie> {
            override fun onItemClicked(position: Int, model: Movie) {
                goToDetail(model)
            }
        })
        if (binding.srlMovie.isRefreshing) {
            binding.srlMovie.isRefreshing = false
            binding.rvMovie.getChildAt(0).overScrollMode = View.OVER_SCROLL_ALWAYS
        }
    }

    override fun onGetMovieListFailure() {
        binding.apply {
            srlMovie.visibility = View.GONE
            rlError.visibility = View.VISIBLE
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
        val details = Details(
            id = model.genre_ids,
            movieId = model.id,
            backdropPath = model.backdrop_path,
            posterPath = model.poster_path,
            title = model.title,
            releaseDate = model.release_date,
            summary = model.overview,
            videos = model.videos,
            isMovie = true
        )
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
        val listLayoutManager = GridLayoutManager(requireContext(), 2)
        movieListAdapter = MovieAdapter(listLayoutManager)
        binding.rvMovie.apply {
            layoutManager = listLayoutManager
            adapter = movieListAdapter
            setOnScrollChangeListener { _, _, _, _, _ ->
                if (listLayoutManager.findLastCompletelyVisibleItemPosition() == (movieListAdapter.itemCount - 1)) {
                    presenter.loadMore()
                }
            }
        }
        binding.srlMovie.setOnRefreshListener {
            binding.rvMovie.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            presenter.resetPagination()
            presenter.getMovieList(page)
        }
    }
}