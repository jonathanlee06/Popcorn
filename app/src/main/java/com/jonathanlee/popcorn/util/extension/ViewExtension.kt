package com.jonathanlee.popcorn.util.extension

import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.applyOnPageSelected(onPageSelected: (Int) -> Unit) {
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) = Unit

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) = Unit

        override fun onPageSelected(position: Int) = onPageSelected.invoke(position)
    })
}