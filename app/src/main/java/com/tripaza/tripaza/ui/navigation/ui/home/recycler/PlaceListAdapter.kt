package com.tripaza.tripaza.ui.navigation.ui.home.recycler

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.databinding.RvItemBinding
import com.tripaza.tripaza.databinding.RvItemHeaderBinding
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_FEATURED
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PLACE
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.helper.StarRatingHelper
import com.tripaza.tripaza.ui.detail.DetailActivity
import kotlin.math.abs
import kotlin.random.Random


class PlaceListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var placeList: ArrayList<Place>
    private lateinit var foodList: ArrayList<Food>
    private lateinit var featuredItem: Item
    private lateinit var onItemClickCallback: OnItemClickCallback
    
    companion object{
        const val HEADER = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0 )
            HeaderViewHolder(RvItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else
            PlaceViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun getItemViewType(position: Int): Int {
        return if (position == HEADER) 0 else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
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
    
    override fun getItemCount(): Int = placeList.size
    
    fun setPlaceList(placeList: ArrayList<Place>){
        placeList.add(0, Place("OFFSET", "OFFSET", "OFFSET", "OFFSET", 0, 0.0, 0.0,
            DUMMY_IMAGE_PLACE
        ))
        this.placeList = placeList
    }
    
    fun setFoodList(foodList: ArrayList<Food>){
        this.foodList = foodList
    }
    
    fun setFeaturedItem(featuredFood: Item){
        this.featuredItem = featuredFood
    }
    
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Place)
    }

    private class HeaderViewHolder(var binding: RvItemHeaderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(foodList: ArrayList<Food>, featuredPlace: Item) {
            HelperTools.glideLoader(binding.root.context, DUMMY_IMAGE_FEATURED, this.binding.ivFeaturedItemImage, false)
            val foodListAdapter = FoodListAdapter()
            foodListAdapter.setFoodList(foodList)
            binding.tvFeaturedItemHeader.text = featuredPlace.name
            binding.tvHeaderPopularIn.text = "Popular in ${featuredPlace.location}"
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
        fun bind(holder: PlaceViewHolder, place: Place) {
            holder.apply {
                this.binding.itemLayout.title.text = place.name
                this.binding.itemLayout.location.text = place.location
                StarRatingHelper.setStarRating(holder.binding.itemLayout.starRating, abs((Random.nextInt())%5) + 1)
                HelperTools.glideLoader(binding.root.context, DUMMY_IMAGE_PLACE, binding.itemLayout.ivItemImages, false)
            }
        }
    }
}