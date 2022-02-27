package com.jonathanlee.popcorn.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    protected val isActive: Boolean
        get() = _isActive
    private var _isActive = false

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _isActive = true
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _isActive = false
    }
}