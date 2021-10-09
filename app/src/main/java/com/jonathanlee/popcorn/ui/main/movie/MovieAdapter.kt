package com.jonathanlee.popcorn.ui.main.movie

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Movie
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemListBinding
import com.jonathanlee.popcorn.util.AdapterItemClickListener

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MoviePosterViewHolder>() {

    private val movieList: MutableList<Movie?> = ArrayList()
    private var context: Context? = null
    private var onItemClickListener: AdapterItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePosterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(
            inflater, parent, false
        )
        context = parent.context
        return MoviePosterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviePosterViewHolder, position: Int) {
        val binding = holder.binding
        if (movieList.isNullOrEmpty()) {
            return
        }
        val data = movieList[position] ?: return
        binding.root.setOnClickListener {
            onItemClickListener?.onItemClick(it, position)
        }
        binding.tvMovieTitle.text = data.title
        context?.let { context ->
            if (data.poster_path != null) {
                val imagePath = Api.getPosterPath(data.poster_path)
                Glide.with(context)
                    .asBitmap()
                    .load(imagePath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                Glide.with(context).clear(binding.ivPoster)
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun setOnItemClickListener(listener: AdapterItemClickListener) {
        onItemClickListener = listener
    }

    fun getItem(position: Int): Movie? {
        return movieList[position]
    }

    fun updateListData(page: Int = 1, list: ArrayList<Movie>) {
        if (page == 1) {
            this.movieList.clear()
        }
        movieList.addAll(list)
        notifyDataSetChanged()
    }

    fun addLoadMore() {
        if (movieList.isNullOrEmpty()) {
            return
        }
        movieList.add(null)
        notifyItemInserted(itemCount - 1)
    }

    fun removeLoadMore() {
        if (movieList.isNullOrEmpty()) {
            return
        }
        movieList.removeAt(itemCount - 1)
        notifyItemRemoved(itemCount)
    }

    inner class MoviePosterViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root)
}