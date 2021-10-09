package com.jonathanlee.popcorn.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Video
import com.jonathanlee.popcorn.databinding.ItemVideoBinding
import com.jonathanlee.popcorn.util.AdapterItemClickListener

class DetailVideoAdapter : RecyclerView.Adapter<DetailVideoAdapter.VideoViewHolder>() {

    private val videoList = ArrayList<Video>()
    private var context: Context? = null
    private var onItemClickListener: AdapterItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoBinding.inflate(
            inflater, parent, false
        )
        context = parent.context
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val binding = holder.binding
        val data = videoList[position]

        binding.rlVideo.setOnClickListener {
            onItemClickListener?.onItemClick(it, position)
        }
        binding.itemVideoTitle.text = data.name
        context?.let {
            Glide.with(it)
                .load(data.thumbnailPath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivVideoThumbnail)
        }
    }

    override fun getItemCount(): Int {
        return if (videoList.size > 5) 5 else videoList.size
    }

    fun setOnItemClickListener(listener: AdapterItemClickListener) {
        onItemClickListener = listener
    }

    fun updateListData(list: ArrayList<Video>) {
        videoList.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class VideoViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root)
}