package com.jonathanlee.popcorn.ui.main.tv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Tv
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemListBinding

class TvAdapter : RecyclerView.Adapter<TvAdapter.TvShowViewHolder>() {

    private val tvShowList = ArrayList<Tv>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(
            inflater, parent, false
        )
        context = parent.context
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val binding = holder.binding
        val data = tvShowList[position]
        binding.tvMovieTitle.text = data.name
        context?.let {
            if (data.poster_path != null) {
                val imagePath = Api.getPosterPath(data.poster_path)
                Glide.with(it)
                    .load(imagePath)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.ivPoster)
            } else {
                Glide.with(it).clear(binding.ivPoster)
            }
        }
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    fun updateListData(list: ArrayList<Tv>) {
        tvShowList.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class TvShowViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

}