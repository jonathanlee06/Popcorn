package com.jonathanlee.popcorn.util.extension

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

fun Context.navigateTo(intent: Intent) {
    this.startActivity(intent)
}

fun Fragment.navigateTo(intent: Intent) {
    this.startActivity(intent)
}