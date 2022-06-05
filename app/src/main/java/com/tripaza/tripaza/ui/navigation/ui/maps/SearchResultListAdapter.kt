package com.tripaza.tripaza.ui.navigation.ui.maps

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.databinding.RvSearchResultItemBinding
import com.tripaza.tripaza.helper.Constants
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PLACE
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.helper.StarRatingHelper
import kotlin.math.abs
import kotlin.random.Random

class SearchResultListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var placeList: ArrayList<Place>
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var DEVELOPMENT_ONLY_CONTEXT: Context  //HELPER ONLY
    fun DEVELOPMENT_ONLY_CONTEXT(DEVELOPMENT_ONLY_CONTEXT: Context){
        this.DEVELOPMENT_ONLY_CONTEXT = DEVELOPMENT_ONLY_CONTEXT
    }
    companion object{
        const val HEADER = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaceViewHolder(RvSearchResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlaceViewHolder).bind(holder, placeList[position])
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(placeList[holder.adapterPosition])}
    }

    override fun getItemCount(): Int = placeList.size

    fun setPlaceList(placeList: ArrayList<Place>){
        this.placeList = placeList
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Place)
    }

    private class PlaceViewHolder(var binding: RvSearchResultItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(holder: PlaceViewHolder, place: Place) {
            holder.apply {
                StarRatingHelper.setStarRating(holder.binding.starRating, abs((Random.nextInt())%5) + 1)
                this.binding.title.text = place.name
                this.binding.description.text = place.description
                HelperTools.glideLoader(binding.root.context, DUMMY_IMAGE_PLACE, binding.icon, false)
            }
        }
    }
}