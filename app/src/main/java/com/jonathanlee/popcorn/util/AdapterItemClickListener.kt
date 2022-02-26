package com.jonathanlee.popcorn.util

interface AdapterItemClickListener<T> {
    fun onItemClicked(position: Int, model: T)
}

interface OptionItemClickListener {
    fun onOptionItemClicked(position: Int)
}