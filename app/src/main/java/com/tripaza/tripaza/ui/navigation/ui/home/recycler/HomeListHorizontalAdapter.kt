package com.tripaza.tripaza.ui.navigation.ui.home.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.RvItemHorizontalBinding
import com.tripaza.tripaza.databinding.RvItemHeaderBinding


class HomeListHorizontalAdapter(private val itemList: ArrayList<ListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(RvItemHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1 
//        return super.getItemViewType(position)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = itemList[position]
        val h = holder as HomeListHorizontalAdapter.ListViewHolder
        h.binding.itemLayout.title.text = data.name
//        holder.binding.rvItemRowStory.text = data.description
//        holder.binding.rvItemRowName.text = data.name
//        holder.binding.rvItemRowDateCreated.text = data.createdAt.toString().substring(0,10)

//        Glide.with(holder.binding.root.context)
//            .load(data.photoUrl)
//            .into(holder.binding.rvItemRowImgBanner)

        // check if night mode activated
//        if ( holder.binding.root.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES)
//            holder.binding.root.setCardBackgroundColor(Color.TRANSPARENT)

        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(itemList[holder.adapterPosition])}
    }

    //    override fun getItemCount(): Int = itemList.size
    override fun getItemCount(): Int = itemList.size

    fun notifyDatasetChangedHelper(){
        notifyDataSetChanged()
    }
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListItem)
    }

    class ListViewHolder(var binding: RvItemHorizontalBinding) : RecyclerView.ViewHolder(binding.root)
}