package com.jonathanlee.popcorn.ui.common.option

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jonathanlee.popcorn.databinding.ItemOptionBinding
import com.jonathanlee.popcorn.util.OptionItemClickListener

class OptionAdapter(
    data: List<Options>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mData: List<Options> = data
    private var context: Context? = null
    private var onOptionItemClickListener: OptionItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        val binding = ItemOptionBinding.inflate(
            inflater, parent, false
        )
        return OptionItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is OptionItemViewHolder) {
            val holderBinding = holder.binding
            val optionData = mData[position]
            holderBinding.root.setOnClickListener {
                onOptionItemClickListener?.onOptionItemClicked(position)
            }
            holderBinding.viewIconChecked.visibility = View.GONE
            holderBinding.tvTitle.text = optionData.text
            holderBinding.viewIcon.setBackgroundResource(optionData.leftIcon)
        }
    }

    fun setOptionItemClickListener(listener: OptionItemClickListener) {
        onOptionItemClickListener = listener
    }

    inner class OptionItemViewHolder(val binding: ItemOptionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return mData.size
    }
}