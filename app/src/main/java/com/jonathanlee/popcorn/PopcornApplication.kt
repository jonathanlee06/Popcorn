package com.jonathanlee.popcorn

import android.app.Application
import com.jonathanlee.popcorn.util.SharedPreferencesUtils

class PopcornApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Init SharedPreferencesUtils
        SharedPreferencesUtils.init(this)
    }
}