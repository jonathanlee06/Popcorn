package com.jonathanlee.popcorn.ui.detail.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jonathanlee.popcorn.data.model.CastItem
import com.jonathanlee.popcorn.data.source.Api
import com.jonathanlee.popcorn.databinding.ItemCastGridBinding
import com.jonathanlee.popcorn.util.OptionItemClickListener

class CastListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val castList = ArrayList<CastItem.Item>()
    private var onClickListener: OptionItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCastGridBinding.inflate(inflater, parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CastViewHolder -> {
                val binding = holder.binding
                val castItem = castList[position]
                val data = castItem.cast
                binding.root.setOnClickListener { onClickListener?.onOptionItemClicked(position) }
                binding.itemCastName.text = data.name
                binding.itemCharacterName.text = data.character
                val imagePath = Api.getCastPath(data.profilePath)
                binding.ivCastPhoto.load(imagePath) {
                    crossfade(true)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    fun setListener(listener: OptionItemClickListener) {
        onClickListener = listener
    }

    fun updateListData(list: List<CastItem.Item>?) {
        list?.let {
            castList.apply {
                clear()
                addAll(list)
            }
        }
        notifyDataSetChanged()
    }

    inner class CastViewHolder(val binding: ItemCastGridBinding) :
        RecyclerView.ViewHolder(binding.root)
}