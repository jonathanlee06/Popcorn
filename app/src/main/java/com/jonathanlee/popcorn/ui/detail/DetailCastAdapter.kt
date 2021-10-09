package com.jonathanlee.popcorn.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Cast
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemCastBinding

class DetailCastAdapter : RecyclerView.Adapter<DetailCastAdapter.CastViewHolder>() {

    private val castList = ArrayList<Cast>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCastBinding.inflate(
            inflater, parent, false
        )
        context = parent.context
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val binding = holder.binding
        val data = castList[position]
        binding.itemCastName.text = data.name
        context?.let {
            val imagePath = Api.getCastPath(data.profilePath)
            Glide.with(it)
                .load(imagePath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivCastPhoto)
        }
    }

    override fun getItemCount(): Int {
        return if (castList.size > 5) 5 else castList.size
    }

    fun updateListData(list: ArrayList<Cast>) {
        castList.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class CastViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root)
}