package com.jonathanlee.popcorn.ui.base

interface BaseContract {
    interface View<T> {
        var presenter: T
    }

    interface Presenter
}