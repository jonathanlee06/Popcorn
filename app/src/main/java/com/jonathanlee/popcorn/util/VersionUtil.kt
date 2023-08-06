package com.jonathanlee.popcorn.util

import android.os.Build

fun isTiramisuOrNewer(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}