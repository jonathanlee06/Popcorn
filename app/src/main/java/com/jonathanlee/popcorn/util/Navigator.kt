package com.jonathanlee.popcorn.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.ui.detail.cast.list.CastListActivity
import com.jonathanlee.popcorn.util.extension.navigateTo
import javax.inject.Singleton

const val EXTRA_CAST_LIST = "castList"

@Singleton
class Navigator {
    fun navigateToCastListPage(context: Context, castList: ArrayList<CastItem.Item>) {
        val intent = Intent(context, CastListActivity::class.java)
        if (context !is Activity) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putParcelableArrayListExtra(EXTRA_CAST_LIST, castList)
        context.navigateTo(intent)
    }

    companion object {
        @Volatile
        private var instance: Navigator? = null

        fun getNavigator() = instance ?: synchronized(this) {
            instance ?: Navigator().also { instance = it }
        }
    }
}