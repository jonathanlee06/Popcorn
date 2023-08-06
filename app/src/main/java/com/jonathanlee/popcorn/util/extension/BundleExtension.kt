package com.jonathanlee.popcorn.util.extension

import android.os.Bundle
import android.os.Parcelable
import com.jonathanlee.popcorn.util.isTiramisuOrNewer
import java.io.Serializable

inline fun <reified T : Serializable> Bundle.getSerializableValue(key: String): T? =
    when {
        isTiramisuOrNewer() -> getSerializable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
    }

inline fun <reified T : Parcelable> Bundle.getParcelableValue(key: String): T? =
    when {
        isTiramisuOrNewer() -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }