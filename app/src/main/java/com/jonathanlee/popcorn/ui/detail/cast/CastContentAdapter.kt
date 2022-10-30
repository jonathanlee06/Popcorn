package com.jonathanlee.popcorn.ui.detail.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jonathanlee.popcorn.data.model.CastCredit
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemContentBinding
import com.jonathanlee.popcorn.util.AdapterItemClickListener

class CastContentAdapter : RecyclerView.Adapter<CastContentAdapter.ContentViewHolder>() {

    private val creditList = ArrayList<CastCredit>()
    private var onItemClickListener: AdapterItemClickListener<CastCredit>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContentBinding.inflate(
            inflater, parent, false
        )
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val binding = holder.binding
        val data = creditList[position]
        binding.ivContentPhoto.load(Api.getPosterPath(data.posterPath)) {
            crossfade(true)
        }
    }

    override fun getItemCount(): Int {
        return creditList.size
    }

    fun setOnItemClickListener(listener: AdapterItemClickListener<CastCredit>) {
        onItemClickListener = listener
    }

    fun updateListData(list: List<CastCredit>) {
        creditList.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class ContentViewHolder(val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root)

}