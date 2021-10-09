package com.jonathanlee.popcorn.ui.main.tv

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Tv
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemListBinding
import com.jonathanlee.popcorn.util.AdapterItemClickListener

class TvAdapter : RecyclerView.Adapter<TvAdapter.TvShowViewHolder>() {

    private val tvShowList = ArrayList<Tv>()
    private var context: Context? = null
    private var onItemClickListener: AdapterItemClickListener? = null

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
        binding.root.setOnClickListener { onItemClickListener?.onItemClick(it, position) }
        binding.tvMovieTitle.text = data.name
        context?.let {
            if (data.poster_path != null) {
                val imagePath = Api.getPosterPath(data.poster_path)
                Glide.with(it)
                    .asBitmap()
                    .load(imagePath)
                    .placeholder(R.drawable.ic_launcher_background)
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource?.let {
                                val palette = Palette.from(resource).generate().darkVibrantSwatch
                                if (palette != null) {
                                    binding.llTitle.setBackgroundColor(palette.rgb)
                                }
                            }
                            return false
                        }

                    })
                    .into(binding.ivPoster)
            } else {
                Glide.with(it).clear(binding.ivPoster)
            }
        }
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    fun setOnItemClickListener(listener: AdapterItemClickListener) {
        onItemClickListener = listener
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