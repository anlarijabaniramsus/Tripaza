package com.tripaza.tripaza.ui.navigation.ui.home.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databinding.RvItemBinding
import com.tripaza.tripaza.databinding.RvItemHeaderBinding
import com.tripaza.tripaza.helper.StarRatingHelper
import kotlin.math.abs
import kotlin.random.Random


class HomeListAdapter(private val itemList: ArrayList<Food>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    companion object{
        const val HEADER = 0
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0 )
            ListViewHolderHeader(RvItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        else
            ListViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun getItemViewType(position: Int): Int {
        return if (position == HEADER) 0 else 1
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0){
            
            val data = arrayListOf<Food>()
            data.add(Food("a", "My A"))
            data.add(Food("b", "My B"))
            data.add(Food("c", "My C"))
            data.add(Food("d", "My D"))
            data.add(Food("e", "My E"))
            data.add(Food("f", "My F"))
            data.add(Food("g", "My G"))
            data.add(Food("h", "My H"))
            data.add(Food("i", "My I"))
            data.add(Food("j", "My J"))
            data.add(Food("k", "My K"))
            data.add(Food("l", "My L"))
            
            (holder as ListViewHolderHeader).bind(data)
            
            
        }else{
            val data = itemList[position]
            (holder as ListViewHolder).apply {
                StarRatingHelper.setStarRating(holder.binding.itemLayout.starRating, abs((Random.nextInt())%5) + 1)
                this.binding.itemLayout.title.text = data.name
                Glide.with(this.binding.root.context)
                    .load(R.drawable.im_places_dummy_images)
                    .into(this.binding.itemLayout.ivItemImages)
            }
        }
        
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(itemList[holder.adapterPosition])}
    }
    
    override fun getItemCount(): Int = itemList.size
    
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Food)
    }

    class ListViewHolder(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)

    class ListViewHolderHeader(var binding: RvItemHeaderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(result: ArrayList<Food>) {
            binding.itemHeaderRvHorizontal
            val homeListHorizontalAdapter = HomeListHorizontalAdapter(result)
            binding.itemHeaderRvHorizontal.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL,false)
            homeListHorizontalAdapter.setOnItemClickCallback(object : HomeListHorizontalAdapter.OnItemClickCallback{
                override fun onItemClicked(data: Food) {
//                val intent = Intent(requireContext(), DetailStoryActivity::class.java)
//                intent.putExtra(DetailStoryActivity.USER_DETAIL_EXTRA, data)
                    Toast.makeText(binding.root.context, "HORIZONTAL Item ${data.name}  Clicked", Toast.LENGTH_SHORT).show()

//                    (binding as HomeListHorizontalAdapter)
                    
                    
//                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@StoriesActivity as Activity).toBundle())
                }
            })
            binding.itemHeaderRvHorizontal.adapter = homeListHorizontalAdapter

        }
    }
}