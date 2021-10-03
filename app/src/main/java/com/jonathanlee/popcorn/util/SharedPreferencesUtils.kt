package com.jonathanlee.popcorn.util

import android.content.Context
import android.content.SharedPreferences


object SharedPreferencesUtils {
    lateinit var mPreferences: SharedPreferences
        private set

    private const val SP_NAME = "Popcorn_SP"

    fun init(context: Context) {
        mPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    fun putBoolean(key: String, value: Boolean) {
        mPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, default: Boolean = false): Boolean {
        val value = mPreferences.getBoolean(key, default)
        return if (value != default) value else default
    }

    fun putString(key: String, value: String?) {
        if (value.isNullOrEmpty()) {
            mPreferences.edit().putString(key, "").apply()
        } else {
            mPreferences.edit().putString(key, value).apply()
        }
    }

    fun getString(key: String, default: String = ""): String {
        val value = mPreferences.getString(key, default)
        return if (value.isNullOrEmpty()) default else value
    }

    fun remove(key: String) {
        mPreferences.edit().remove(key).apply()
    }
}