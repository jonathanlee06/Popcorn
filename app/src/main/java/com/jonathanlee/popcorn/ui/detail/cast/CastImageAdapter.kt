package com.jonathanlee.popcorn.ui.detail.cast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemCastImageBinding

class CastImageAdapter : RecyclerView.Adapter<CastImageAdapter.ItemViewHolder>() {

    private val images: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCastImageBinding.inflate(
            inflater, parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val binding = holder.binding
        val image = images[position]
        binding.tvMovieTitle.visibility = View.GONE
        binding.tvPlaceholderTitle.visibility = View.GONE
        val imagePath = Api.getPosterPath(image)
        binding.ivPoster.load(imagePath) {
            crossfade(true)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun updateListData(list: ArrayList<String>) {
        images.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(val binding: ItemCastImageBinding) :
        RecyclerView.ViewHolder(binding.root)
}