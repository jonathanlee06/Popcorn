package com.jonathanlee.popcorn.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.jonathanlee.bindingdelegate.ext.viewBinding
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.databinding.ActivityMainBinding
import com.jonathanlee.popcorn.ui.main.movie.MovieFragment
import com.jonathanlee.popcorn.ui.main.tv.TvFragment
import com.jonathanlee.popcorn.ui.search.SearchActivity
import com.jonathanlee.popcorn.util.extension.applyOnPageSelected
import com.jonathanlee.popcorn.util.extension.navigateTo
import com.jonathanlee.popcorn.util.network.ConnectivityObserver
import com.jonathanlee.popcorn.util.network.NetworkConnectivityObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(this)
        setContentView(binding.root)
        initView()
        observe()
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
        binding.mainToolbar.ivSearch.setOnClickListener {
            navigateTo(SearchActivity.getStartIntent(this))
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            connectivityObserver.observe().collectLatest {
                val networkAvailable = it == ConnectivityObserver.Status.Available
                binding.mainToolbar.ivSearch.isVisible = networkAvailable
            }
        }
    }
}