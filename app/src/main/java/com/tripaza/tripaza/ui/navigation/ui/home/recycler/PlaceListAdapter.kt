package com.tripaza.tripaza.ui.navigation.ui.home.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.databinding.RvItemBinding
import com.tripaza.tripaza.databinding.RvItemHeaderBinding
import com.tripaza.tripaza.helper.StarRatingHelper
import kotlin.math.abs
import kotlin.random.Random


class PlaceListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var placeList: ArrayList<Place>
    private lateinit var foodList: ArrayList<Food>
    private lateinit var featuredPlace: Place
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var DEVELOPMENT_ONLY_CONTEXT: Context  //HELPER ONLY
    fun DEVELOPMENT_ONLY_CONTEXT(DEVELOPMENT_ONLY_CONTEXT: Context){
        this.DEVELOPMENT_ONLY_CONTEXT = DEVELOPMENT_ONLY_CONTEXT
    }
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
            (holder as HeaderViewHolder).bind(foodList, featuredPlace)
            
            val handler = fun(){
                Toast.makeText(DEVELOPMENT_ONLY_CONTEXT, "Featured Item Clicked", Toast.LENGTH_SHORT).show()
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
        this.placeList = placeList
    }
    
    fun setFoodList(foodList: ArrayList<Food>){
        this.foodList = foodList
    }
    fun setFeaturedFood(featuredFood: Place){
        this.featuredPlace = featuredFood
    }
    
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Place)
    }

    private class HeaderViewHolder(var binding: RvItemHeaderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(foodList: ArrayList<Food>, featuredPlace: Place) {
            binding.itemHeaderRvHorizontal
            val foodListAdapter = FoodListAdapter()
            foodListAdapter.setFoodList(foodList)
            binding.tvFeaturedItemHeader.text = featuredPlace.name
            binding.tvHeaderPopularIn.text = "Popular in ${featuredPlace.location}"
            binding.tvFeaturedItemDescription.text = featuredPlace.description
            binding.itemHeaderRvHorizontal.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            foodListAdapter.setOnItemClickCallback(object : FoodListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: Food) {
                    Toast.makeText(binding.root.context, "HORIZONTAL Item ${data.name}  Clicked", Toast.LENGTH_SHORT).show()
                }
            })
            binding.itemHeaderRvHorizontal.adapter = foodListAdapter
        }
    }
    
    private class PlaceViewHolder(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(holder: PlaceViewHolder, place: Place) {
            holder.apply {
                StarRatingHelper.setStarRating(holder.binding.itemLayout.starRating, abs((Random.nextInt())%5) + 1)
                this.binding.itemLayout.title.text = place.name
                Glide.with(this.binding.root.context)
                    .load(R.drawable.im_places_dummy_images)
                    .into(this.binding.itemLayout.ivItemImages)
            }
        }
    }
}