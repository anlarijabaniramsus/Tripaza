package com.tripaza.tripaza.ui.camera.recycler

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tripaza.tripaza.databinding.RvDetectResultItemBinding
import com.tripaza.tripaza.ml.MLFoodModel

class DetectResultAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var detectionResultList: ArrayList<MLFoodModel.DetectionResult>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListViewHolder(RvDetectResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = detectionResultList[position]
        (holder as ListViewHolder).apply {
            val score = data.scoreAsFloat * 100
            
            val title = data.categoryAsString.replaceFirstChar { 
                it.uppercase()
            }
            
            this.binding.scoreLevel.incrementProgressBy(1)
            this.binding.scoreLevel.setProgress(Math.abs(score.toInt()))
            this.binding.title.text = title
            this.binding.percent.text = "$score%"
            if (score < 33){
                this.binding.percent.setTextColor(Color.RED)
                this.binding.scoreLevel.progressTintList = ColorStateList.valueOf(Color.RED)
            }else if(score < 66){
                this.binding.percent.setTextColor(Color.YELLOW)
                this.binding.scoreLevel.progressTintList = ColorStateList.valueOf(Color.YELLOW)
            }else{
                this.binding.percent.setTextColor(Color.GREEN)
                this.binding.scoreLevel.progressTintList = ColorStateList.valueOf(Color.GREEN)
            }
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
    }

    override fun getItemCount(): Int = detectionResultList.size

    fun setDetectionResultList(detectionResultList: ArrayList<MLFoodModel.DetectionResult>){
        this.detectionResultList = detectionResultList
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MLFoodModel.DetectionResult)
    }

    class ListViewHolder(var binding: RvDetectResultItemBinding) : RecyclerView.ViewHolder(binding.root)
}