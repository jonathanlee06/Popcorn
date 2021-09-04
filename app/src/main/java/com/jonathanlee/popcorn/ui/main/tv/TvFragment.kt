package com.jonathanlee.popcorn.ui.main.tv

import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.databinding.FragmentTvBinding
import com.jonathanlee.popcorn.ui.base.BaseFragment
import com.jonathanlee.popcorn.util.binding.viewBinding

class TvFragment : BaseFragment() {
    override val layoutResId: Int = R.layout.fragment_tv
    override val binding: FragmentTvBinding by viewBinding()
}