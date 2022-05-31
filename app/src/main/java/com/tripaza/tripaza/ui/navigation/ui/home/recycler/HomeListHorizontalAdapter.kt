package com.tripaza.tripaza.ui.navigation.ui.home.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databinding.RvItemBinding
import com.tripaza.tripaza.databinding.RvItemHorizontalBinding
import com.tripaza.tripaza.databinding.StarRatingBinding
import com.tripaza.tripaza.helper.StarRatingHelper
import kotlin.math.abs
import kotlin.random.Random

class HomeListHorizontalAdapter(private val itemList: ArrayList<Food>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(RvItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1 
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = itemList[position]
        (holder as ListViewHolder).apply {
            StarRatingHelper.setStarRating(this.binding.itemLayout.starRating, abs((Random.nextInt())%5) + 1)
            this.binding.itemLayout.title.text = data.name
            Glide.with(holder.binding.root.context)
                .load(R.drawable.im_places_dummy_images)
                .into(holder.binding.itemLayout.ivItemImages)
            this.itemView.setOnClickListener { onItemClickCallback.onItemClicked(itemList[holder.adapterPosition]) }
        }
    }
    
    override fun getItemCount(): Int = itemList.size
    
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Food)
    }

    class ListViewHolder(var binding: RvItemHorizontalBinding) : RecyclerView.ViewHolder(binding.root)
}