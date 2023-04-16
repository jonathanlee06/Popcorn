package com.jonathanlee.popcorn.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jonathanlee.bindingdelegate.ext.viewBinding
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.databinding.ActivityMainBinding
import com.jonathanlee.popcorn.ui.main.movie.MovieFragment
import com.jonathanlee.popcorn.ui.main.tv.TvFragment
import com.jonathanlee.popcorn.util.extension.applyOnPageSelected

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val fragments: ArrayList<Fragment> = arrayListOf(
            MovieFragment(),
            TvFragment()
        )
        binding.vpMain.apply {
            adapter = MainAdapter(fragments, this@MainActivity)
            offscreenPageLimit = 2
            applyOnPageSelected { binding.bottomNavigation.menu.getItem(it).isChecked = true }
        }
        TabLayoutMediator(binding.tab, binding.vpMain) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.menu_movie)
                1 -> tab.text = getString(R.string.menu_tv)
            }
        }.attach()
        binding.titleBar.apply {
            visibility = View.VISIBLE
        }
    }
}