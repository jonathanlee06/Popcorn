package com.jonathanlee.popcorn.util.extension

import android.content.Intent
import android.os.Parcelable
import com.jonathanlee.popcorn.util.isTiramisuOrNewer

inline fun <reified T : Parcelable> Intent.getParcelableArrayList(
    name: String,
    clazz: Class<out T>,
): ArrayList<T>? {
    return if (isTiramisuOrNewer()) {
        getParcelableArrayListExtra(name, clazz)
    } else {
        @Suppress("DEPRECATION")
        getParcelableArrayListExtra(name)
    }
}

inline fun <reified T : Parcelable> Intent.getParcelable(
    name: String,
    clazz: Class<out T>,
): T? {
    return if (isTiramisuOrNewer()) {
        getParcelableExtra(name, clazz)
    } else {
        @Suppress("DEPRECATION")
        getParcelableExtra(name)
    }
}