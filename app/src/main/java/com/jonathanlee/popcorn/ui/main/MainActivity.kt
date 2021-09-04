package com.jonathanlee.popcorn.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.databinding.ActivityMainBinding
import com.jonathanlee.popcorn.util.binding.viewBinding
import com.jonathanlee.popcorn.util.extension.applyOnPageSelected

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.vpMain.apply {
            adapter = MainAdapter(supportFragmentManager, lifecycle)
            offscreenPageLimit = 3
            applyOnPageSelected { binding.bottomNavigation.menu.getItem(it).isChecked = true }
            binding.bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.action_one -> currentItem = 0
                    R.id.action_two -> currentItem = 1
                    R.id.action_three -> currentItem = 2
                }
                true
            }
        }
    }
}