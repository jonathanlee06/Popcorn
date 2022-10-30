package com.jonathanlee.popcorn.ui.detail

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemCastBinding
import com.jonathanlee.popcorn.databinding.ItemCastMoreBinding

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
                val binding = ItemCastBinding.inflate(
                    inflater, parent, false
                )
                CastViewHolder(binding)
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
                val binding = holder.binding
                val castItem = castList[position] as CastItem.Item
                val data = castItem.cast
                binding.itemCastName.text = data.name
                binding.itemCharacterName.text = data.character
                val imagePath = Api.getPosterPath(data.profilePath)
                binding.ivCastPhoto.load(imagePath) {
                    crossfade(true)
                }
                binding.root.setOnClickListener {
                    onClickListener?.onCastClick(position)
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

    inner class CastViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root)

    inner class MoreViewHolder(val binding: ItemCastMoreBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface CastOnClickListener {
        fun onCastClick(position: Int)
        fun onMoreClick()
    }
}