package com.jonathanlee.popcorn.ui.base

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.ui.common.DialogHelper

abstract class BaseFragment(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    protected val mFragmentManager: FragmentManager
        get() = childFragmentManager

    protected fun showNetworkErrorDialog(
        context: Context,
        positiveButtonCallback: (() -> Unit)? = null,
        negativeButtonCallback: (() -> Unit)? = null,
    ) {
        DialogHelper.showNetworkErrorDialog(
            context = context,
            positiveBtnCallback = positiveButtonCallback,
            negativeBtnCallback = negativeButtonCallback
        )
    }

    protected fun refreshController(
        srl: SwipeRefreshLayout,
        isListOccupied: Boolean,
        list: RecyclerView
    ) {
        if (srl.isRefreshing) {
            srl.isRefreshing = false
            overScrollController(
                isListOccupied = isListOccupied,
                isFinishRefresh = true,
                list = list
            )
        }
    }

    protected fun overScrollController(
        isListOccupied: Boolean,
        isFinishRefresh: Boolean,
        list: RecyclerView
    ) {
        if (isListOccupied) {
            list.getChildAt(0).overScrollMode = if (isFinishRefresh) {
                View.OVER_SCROLL_ALWAYS
            } else {
                View.OVER_SCROLL_NEVER
            }
        }
    }

    protected fun getListIcon(spanCount: Int?): Int {
        return if (spanCount == 2) {
            R.drawable.ic_list_view
        } else {
            R.drawable.ic_grid_view
        }
    }
}