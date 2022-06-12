package com.tripaza.tripaza.ui.navigation.ui.home.recycler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.databinding.RvItemBinding
import com.tripaza.tripaza.databinding.RvItemHeaderBinding
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PLACE
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.helper.StarRatingHelper
import com.tripaza.tripaza.ui.detail.DetailActivity
import kotlin.math.abs
import kotlin.random.Random


class PlaceListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var placeList = ArrayList<Food>()
    private var foodList = ArrayList<Food>()
    private lateinit var featuredItem: Food
    private lateinit var onItemClickCallback: OnItemClickCallback
    
    companion object{
        const val HEADER = 0
        private const val TAG = "PlaceListAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder ")
        return if (viewType == 0 )
            HeaderViewHolder(RvItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else
            PlaceViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun getItemViewType(position: Int): Int {
        Log.d(TAG, "getItemViewType ")
        return if (position == HEADER) 0 else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder ")
        if (position == 0){
            (holder as HeaderViewHolder).bind(foodList, featuredItem)
            
            val handler = fun(){
                val bundle = Bundle()
                bundle.putParcelable(DetailActivity.EXTRA_DATA, featuredItem)
                val intent = Intent(holder.binding.root.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_BUNDLE, bundle)
                holder.binding.root.context.startActivity(intent)
            }
            
            holder.binding.tvFeaturedItemHeader.setOnClickListener { handler() }
            holder.binding.tvFeaturedItemDescription.setOnClickListener { handler() }
            holder.binding.ivFeaturedItemImage.setOnClickListener { handler() }
        }else{
            (holder as PlaceViewHolder).bind(holder, placeList[position])
            holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(placeList[holder.adapterPosition])}
        }
    }
    
//    override fun getItemCount(): Int = placeList.size

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ITEM COUNT : ${placeList.size}")
        return placeList.size + 1
    }
    
    fun setPlaceList(placeList: ArrayList<Food>){
        Log.d(TAG, "setPlaceList")
        this.placeList = placeList
        notifyDataSetChanged()
    }
    
    fun setFoodList(foodList: ArrayList<Food>){
        Log.d(TAG, "setFoodList ")
        this.foodList = foodList
    }
    
    fun setFeaturedFood(featuredFood: Food){
        Log.d(TAG, "setFeaturedItem ")
        this.featuredItem = featuredFood
    }
    
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Food)
    }

    private class HeaderViewHolder(var binding: RvItemHeaderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(foodList: ArrayList<Food>, featuredPlace: Food) {
            Log.d(TAG, "bind: HeaderViewHolder")
            HelperTools.glideLoaderRounded(binding.root.context, featuredPlace.image, this.binding.ivFeaturedItemImage)
            
            val foodListAdapter = FoodListAdapter()
            foodListAdapter.setFoodList(foodList)
            binding.tvFeaturedItemHeader.text = featuredPlace.name
            binding.tvHeaderPopularIn.text = "Popular in Bali"
            binding.tvFeaturedItemDescription.text = featuredPlace.description
            binding.itemHeaderRvHorizontal.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            foodListAdapter.setOnItemClickCallback(object : FoodListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: Food) {
                    val bundle = Bundle()
                    bundle.putParcelable(DetailActivity.EXTRA_DATA, data)
                    val intent = Intent(binding.root.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_BUNDLE, bundle)
                    binding.root.context.startActivity(intent)
                }
            })
            binding.itemHeaderRvHorizontal.adapter = foodListAdapter
        }
    }
    
    private class PlaceViewHolder(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(holder: PlaceViewHolder, place: Food) {
            Log.d(TAG, "bind: PlaceViewHolder")
            holder.apply {
                this.binding.itemLayout.title.text = place.name
                this.binding.itemLayout.location.text = place.location
                StarRatingHelper.setStarRating(holder.binding.itemLayout.starRating, place.rating)
                HelperTools.glideLoaderRounded(binding.root.context, DUMMY_IMAGE_PLACE, binding.itemLayout.ivItemImages)
            }
        }
    }
}