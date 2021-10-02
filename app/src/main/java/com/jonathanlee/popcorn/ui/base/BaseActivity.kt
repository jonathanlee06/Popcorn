package com.jonathanlee.popcorn.ui.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    protected fun getStatusBarSize(): Int {
        val idStatusBarHeight = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (idStatusBarHeight > 0) {
            resources.getDimensionPixelSize(idStatusBarHeight)
        } else {
            0
        }
    }
}