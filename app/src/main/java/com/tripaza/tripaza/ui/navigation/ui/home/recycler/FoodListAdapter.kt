package com.tripaza.tripaza.ui.navigation.ui.home.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databinding.RvItemHorizontalBinding
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.helper.StarRatingHelper
import kotlin.math.abs
import kotlin.random.Random

class FoodListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var listHorizontal = ArrayList<Food>()
    companion object{
        private const val TAG = "FoodListAdapter"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(RvItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1 
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = listHorizontal[position]
        (holder as ListViewHolder).apply {
            StarRatingHelper.setStarRating(this.binding.itemLayout.starRating, data.rating)
            this.binding.itemLayout.title.text = data.name
            this.binding.itemLayout.location.text = data.location
            HelperTools.glideLoaderRounded(binding.root.context, data.image, binding.itemLayout.ivItemImages)
            this.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listHorizontal[holder.adapterPosition]) }
        }
    }
    
    override fun getItemCount(): Int{
        Log.d(TAG, "getItemCount: ${listHorizontal.size}")
        return listHorizontal.size
    }

    fun setFoodList(foodList: ArrayList<Food>){
        Log.d(TAG, "setFoodList: array list input to adapter size: ${foodList.size} ")
        this.listHorizontal = foodList
    }
    
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    
    interface OnItemClickCallback {
        fun onItemClicked(data: Food)
    }

    class ListViewHolder(var binding: RvItemHorizontalBinding) : RecyclerView.ViewHolder(binding.root)
}