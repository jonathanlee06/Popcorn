package com.jonathanlee.popcorn.ui.main.star

import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.databinding.FragmentStarBinding
import com.jonathanlee.popcorn.ui.base.BaseFragment
import com.jonathanlee.popcorn.util.binding.viewBinding

class StarFragment : BaseFragment() {
    override val layoutResId: Int = R.layout.fragment_star
    override val binding: FragmentStarBinding by viewBinding()

}