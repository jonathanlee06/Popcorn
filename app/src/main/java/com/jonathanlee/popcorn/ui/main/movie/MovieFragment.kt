package com.jonathanlee.popcorn.ui.main.movie

import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.databinding.FragmentMovieBinding
import com.jonathanlee.popcorn.ui.base.BaseFragment
import com.jonathanlee.popcorn.util.binding.viewBinding

class MovieFragment : BaseFragment() {

    override val layoutResId: Int = R.layout.fragment_movie
    override val binding: FragmentMovieBinding by viewBinding()


}