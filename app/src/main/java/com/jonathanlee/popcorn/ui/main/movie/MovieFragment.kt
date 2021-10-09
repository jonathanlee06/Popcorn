package com.jonathanlee.popcorn.ui.main.movie

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Details
import com.jonathanlee.popcorn.data.model.Movie
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
    private val movieList: ArrayList<Movie> = ArrayList()
    private var page: Int = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initPresenter()
    }

    override fun onGetMovieListSuccess(page: Int, movies: ArrayList<Movie>) {
        binding.apply {
            srlMovie.visibility = View.VISIBLE
            rlError.visibility = View.GONE
        }
        movieListAdapter.updateListData(page, movies)
        movieListAdapter.setOnItemClickListener(object : AdapterItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                goToDetail(position)
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
        movieListAdapter.addLoadMore()
    }

    override fun removeLoadMore() {
        movieListAdapter.removeLoadMore()
    }

    private fun goToDetail(position: Int) {
        val movieAtPosition = movieListAdapter.getItem(position) ?: return
        val details = Details(
            id = movieAtPosition.genre_ids,
            movieId = movieAtPosition.id,
            backdropPath = movieAtPosition.backdrop_path,
            title = movieAtPosition.title,
            releaseDate = movieAtPosition.release_date,
            summary = movieAtPosition.overview,
            videos = movieAtPosition.videos,
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
        movieListAdapter = MovieAdapter()
        val listLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvMovie.apply {
            layoutManager = listLayoutManager
            adapter = movieListAdapter
            setOnScrollChangeListener { v, _, _, _, _ ->
                if (!v.canScrollVertically(1)) {
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