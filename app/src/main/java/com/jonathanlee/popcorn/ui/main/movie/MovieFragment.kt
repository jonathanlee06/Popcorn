package com.jonathanlee.popcorn.ui.main.movie

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Movie
import com.jonathanlee.popcorn.data.repository.Repository
import com.jonathanlee.popcorn.databinding.FragmentMovieBinding
import com.jonathanlee.popcorn.ui.base.BaseFragment
import com.jonathanlee.popcorn.util.AdapterItemClickListener
import com.jonathanlee.popcorn.util.binding.viewBinding

class MovieFragment : BaseFragment(), MovieContract.View {

    override val layoutResId: Int = R.layout.fragment_movie
    override val binding: FragmentMovieBinding by viewBinding()
    override lateinit var presenter: MovieContract.Presenter
    private lateinit var movieListAdapter: MovieAdapter
    private val movieList: ArrayList<Movie> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initPresenter()
    }

    override fun onGetMovieListSuccess(movies: ArrayList<Movie>) {
        binding.apply {
            srlMovie.visibility = View.VISIBLE
            rlError.visibility = View.GONE
        }
        movieList.clear()
        movieList.addAll(movies)
        movieListAdapter.updateListData(movieList)
        movieListAdapter.setOnItemClickListener(object : AdapterItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                //throw RuntimeException("Firebase Test Crash")
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

    private fun initPresenter() {
        presenter = MoviePresenter(
            Repository.provideMovieListRepository(),
            this,
            lifecycleScope
        )
        presenter.getMovieList()
    }

    private fun initView() {
        movieListAdapter = MovieAdapter()
        binding.rvMovie.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieListAdapter
        }
        binding.srlMovie.setOnRefreshListener {
            binding.rvMovie.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER
            presenter.getMovieList()
        }
    }
}