package com.jonathanlee.popcorn.ui.main.movie

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jonathanlee.popcorn.data.model.Movie
import com.jonathanlee.popcorn.data.model.MovieItem
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemListBinding
import com.jonathanlee.popcorn.databinding.ItemLoadingBinding
import com.jonathanlee.popcorn.util.AdapterItemClickListener

class MovieAdapter(private val layoutManager: GridLayoutManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val movieList: MutableList<MovieItem> = ArrayList()
    private var context: Context? = null
    private var onItemClickListener: AdapterItemClickListener<Movie>? = null

    companion object {
        private const val TYPE_FOOTER = 0
        private const val TYPE_ITEM = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return when (viewType) {
            TYPE_FOOTER -> {
                val binding = ItemLoadingBinding.inflate(
                    inflater, parent, false
                )
                FooterViewHolder(binding)
            }
            TYPE_ITEM -> {
                val binding = ItemListBinding.inflate(
                    inflater, parent, false
                )
                ItemViewHolder(binding)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                val binding = holder.binding
                if (movieList.isNullOrEmpty()) {
                    return
                }
                val model = movieList[position] as MovieItem.Item
                binding.root.setOnClickListener {
                    onItemClickListener?.onItemClicked(position, model.movie)
                }
                binding.tvMovieTitle.text = model.movie.title
                binding.tvPlaceholderTitle.text = model.movie.title
                context?.let { context ->
                    if (model.movie.poster_path != null) {
                        val imagePath = Api.getPosterPath(model.movie.poster_path)
                        Glide.with(context)
                            .asBitmap()
                            .load(imagePath)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .listener(object : RequestListener<Bitmap> {
                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Bitmap>?,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    binding.tvPlaceholderTitle.visibility = View.VISIBLE
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
                                        val palette =
                                            Palette.from(resource).generate().vibrantSwatch
                                        if (palette != null) {
                                            binding.llTitle.setBackgroundColor(palette.rgb)
                                        }
                                    }
                                    return false
                                }

                            })
                            .into(binding.ivPoster)
                    } else {
                        Glide.with(context).clear(binding.ivPoster)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (movieList[position]) {
            is MovieItem.Footer -> TYPE_FOOTER
            is MovieItem.Item -> TYPE_ITEM
        }
    }

    fun setOnItemClickListener(listener: AdapterItemClickListener<Movie>) {
        onItemClickListener = listener
    }

    fun getItem(position: Int): MovieItem {
        return movieList[position]
    }

    fun updateListData(page: Int = 1, list: List<MovieItem.Item>?) {
        if (page == 1) {
            this.movieList.clear()
        }
        if (!list.isNullOrEmpty()) {
            movieList.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addLoadMore() {
        if (movieList.isNullOrEmpty()) {
            return
        }
        movieList.add(MovieItem.Footer)
        notifyItemInserted(itemCount - 1)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == (itemCount - 1)) {
                    2
                } else {
                    1
                }
            }

        }
    }

    fun removeLoadMore() {
        if (movieList.isNullOrEmpty()) {
            return
        }
        movieList.removeAt(itemCount - 1)
        notifyItemRemoved(itemCount)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == (itemCount)) {
                    2
                } else {
                    1
                }
            }
        }
    }

    inner class ItemViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class FooterViewHolder(val binding: ItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

}