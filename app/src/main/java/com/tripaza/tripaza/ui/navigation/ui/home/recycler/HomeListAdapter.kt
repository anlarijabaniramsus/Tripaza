package com.tripaza.tripaza.ui.navigation.ui.home.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.RvItemBinding
import com.tripaza.tripaza.databinding.RvItemHeaderBinding


class HomeListAdapter(private val itemList: ArrayList<ListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0 )
            return ListViewHolderHeader(RvItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)) 
//            return ListViewHolderHeader(LayoutInflater.from(parent.context), parent, false) 
        else
            return ListViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1 
//        return super.getItemViewType(position)
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0){
            
            val data = arrayListOf<ListItem>()
            data.add(ListItem("a", "My A"))
            data.add(ListItem("b", "My B"))
            data.add(ListItem("c", "My C"))
            data.add(ListItem("d", "My D"))
            data.add(ListItem("e", "My E"))
            data.add(ListItem("f", "My F"))
            data.add(ListItem("g", "My G"))
            data.add(ListItem("h", "My H"))
            data.add(ListItem("i", "My I"))
            data.add(ListItem("j", "My J"))
            data.add(ListItem("k", "My K"))
            data.add(ListItem("l", "My L"))
            
            (holder as ListViewHolderHeader).bind(data)
//            LinearLayoutManager.VERTICAL,false
            
        }else{
            val data = itemList[position]
            (holder as ListViewHolder).binding.textView.text = data.name
            
        }
        
//        val h = holder as ListViewHolder
        
        
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

    class ListViewHolder(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)

    class ListViewHolderHeader(var binding: RvItemHeaderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: ArrayList<ListItem>) {
            binding.itemHeaderRvHorizontal
            val homeListHorizontalAdapter = HomeListHorizontalAdapter(result)
            binding.itemHeaderRvHorizontal.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            binding.itemHeaderRvHorizontal.adapter = homeListHorizontalAdapter

        }
    }
}