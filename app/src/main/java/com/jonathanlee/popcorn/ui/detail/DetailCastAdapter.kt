package com.jonathanlee.popcorn.ui.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.databinding.ItemCastMoreBinding
import com.jonathanlee.popcorn.ui.detail.cast.list.DetailCastItem
import com.jonathanlee.popcorn.ui.theme.PopcornTheme

class DetailCastAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val castList = ArrayList<CastItem>()
    private var context: Context? = null
    private var onClickListener: CastOnClickListener? = null

    companion object {
        private const val TYPE_ITEM = 0
        private const val TYPE_MORE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return when (viewType) {
            TYPE_ITEM -> {
                CastViewHolder(ComposeView(parent.context))
            }
            TYPE_MORE -> {
                val binding = ItemCastMoreBinding.inflate(
                    inflater, parent, false
                )
                MoreViewHolder(binding)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CastViewHolder -> {
                val view = holder.composeView
                val castItem = castList[position] as CastItem.Item
                val data = castItem.cast
                view.setContent {
                    PopcornTheme {
                        DetailCastItem(
                            cast = data,
                            onClick = { onClickListener?.onCastClick(position) }
                        )
                    }
                }
            }
            is MoreViewHolder -> {
                val binding = holder.binding
                binding.root.setOnClickListener {
                    onClickListener?.onMoreClick()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (castList.size > 6) 6 else castList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (castList[position]) {
            is CastItem.Item -> TYPE_ITEM
            is CastItem.More -> TYPE_MORE
        }
    }

    fun setListener(listener: CastOnClickListener) {
        onClickListener = listener
    }

    fun updateListData(list: List<CastItem.Item>?) {
        list?.let {
            val listWithMore: MutableList<CastItem> = ArrayList<CastItem>().apply {
                addAll(list.take(5))
                if (list.size > 5) add(CastItem.More)
            }
            Log.d("cast", "updateListData: listSize=${listWithMore.size}")
            castList.apply {
                clear()
                addAll(listWithMore)
            }
        }
        notifyDataSetChanged()
    }

    inner class CastViewHolder(val composeView: ComposeView) : RecyclerView.ViewHolder(composeView)

    inner class MoreViewHolder(val binding: ItemCastMoreBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface CastOnClickListener {
        fun onCastClick(position: Int)
        fun onMoreClick()
    }
}