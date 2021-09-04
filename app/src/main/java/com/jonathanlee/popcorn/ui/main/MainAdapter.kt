package com.jonathanlee.popcorn.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jonathanlee.popcorn.ui.main.movie.MovieFragment
import com.jonathanlee.popcorn.ui.main.star.StarFragment
import com.jonathanlee.popcorn.ui.main.tv.TvFragment

class MainAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment()
            1 -> TvFragment()
            else -> StarFragment()
        }
    }

}