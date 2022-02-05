package com.jonathanlee.popcorn.ui.common

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.jonathanlee.popcorn.R

class DialogHelper {

    companion object {

        fun showGenericErrorDialog(
            context: Context,
            title: String? = null,
            msg: String? = null,
            positiveBtnCallback: (() -> Unit)? = null,
            negativeBtnCallback: (() -> Unit)? = null
        ) {
            show(
                context = context,
                builder = DialogBuilder(
                    title = title ?: context.getString(R.string.dialog_common_error_title),
                    msg = msg ?: context.getString(R.string.dialog_common_error_message),
                    positiveMsg = context.getString(R.string.dialog_common_error_btn_ok),
                    positiveBtnCallback = positiveBtnCallback,
                    negativeBtnCallback = negativeBtnCallback
                )
            )
        }

        fun showNetworkErrorDialog(
            context: Context,
            positiveBtnCallback: (() -> Unit)? = null,
            negativeBtnCallback: (() -> Unit)? = null
        ) {
            show(
                context = context,
                builder = DialogBuilder(
                    title = context.getString(R.string.dialog_common_error_connection_title),
                    msg = context.getString(R.string.dialog_common_error_connection_message),
                    positiveMsg = context.getString(R.string.dialog_common_error_btn_ok),
                    positiveBtnCallback = positiveBtnCallback,
                    negativeBtnCallback = negativeBtnCallback
                )
            )
        }

        private fun show(
            context: Context,
            builder: DialogBuilder
        ) {
            MaterialDialog(context).show {
                val title = if (builder.title.isNullOrEmpty()) {
                    context.getString(R.string.dialog_common_error_title)
                } else {
                    builder.title
                }
                val msg = if (builder.msg.isNullOrEmpty()) {
                    context.getString(R.string.dialog_common_error_message)
                } else {
                    builder.msg
                }
                title(text = title)
                message(text = msg)
                cancelable(builder.isCancellable)
                builder.positiveMsg?.let {
                    positiveButton(text = it) { dialog ->
                        dialog.dismiss()
                        builder.positiveBtnCallback?.invoke()
                    }
                }
                builder.negativeMsg?.let {
                    negativeButton(text = it) { dialog ->
                        dialog.dismiss()
                        builder.negativeBtnCallback?.invoke()
                    }
                }
                setOnDismissListener { builder.positiveBtnCallback?.invoke() }
            }
        }

    }

}

data class DialogBuilder(
    val title: String? = null,
    val msg: String? = null,
    val isCancellable: Boolean = true,
    val positiveMsg: String? = null,
    val negativeMsg: String? = null,
    val positiveBtnCallback: (() -> Unit)? = null,
    val negativeBtnCallback: (() -> Unit)? = null
)