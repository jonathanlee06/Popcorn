package com.jonathanlee.popcorn.util.extension

import android.content.res.Resources
import android.util.TypedValue

val Float.dp: Float
    get() = convert(this, TypedValue.COMPLEX_UNIT_DIP)

val Int.dp: Int
    get() = convert(this.toFloat(), TypedValue.COMPLEX_UNIT_DIP).toInt()

val Float.sp: Float
    get() = convert(this, TypedValue.COMPLEX_UNIT_SP)

val Int.sp: Int
    get() = convert(this.toFloat(), TypedValue.COMPLEX_UNIT_SP).toInt()

private fun convert(value: Float, type: Int): Float {
    val displayMetrics = Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(type, value, displayMetrics)
}