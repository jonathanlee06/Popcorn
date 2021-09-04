package com.jonathanlee.popcorn.ui.main.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonathanlee.popcorn.R
import com.jonathanlee.popcorn.data.model.Movie
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemListBinding

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MoviePosterViewHolder>() {

    private val movieList = ArrayList<Movie>()
    private var context: Context? = null

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
        val data = movieList[position]
        binding.tvMovieTitle.text = data.title
        if (data.poster_path != null && context != null) {
            val imagePath = Api.getPosterPath(data.poster_path)
            Glide.with(context!!)
                .load(imagePath)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.ivPoster)
        } else {
            Glide.with(context!!).clear(binding.ivPoster)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun getItem(position: Int): Movie {
        return movieList[position]
    }

    fun updateListData(list: ArrayList<Movie>) {
        movieList.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    inner class MoviePosterViewHolder(val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root)
}