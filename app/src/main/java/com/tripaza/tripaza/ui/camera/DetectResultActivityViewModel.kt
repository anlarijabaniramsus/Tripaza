package com.tripaza.tripaza.ui.camera

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.ml.MLFoodModel

class DetectResultActivityViewModel: ViewModel() {
    private val _photoBitmap = MutableLiveData<Bitmap>()
    val photoBitmap: LiveData<Bitmap> = _photoBitmap
    
    fun setPhotoBitmap(bitmapParcel: Bitmap){
        _photoBitmap.value = bitmapParcel
    }

    private val _detectionResultList = MutableLiveData<ArrayList<MLFoodModel.DetectionResult>>()
    val detectionResultList: LiveData<ArrayList<MLFoodModel.DetectionResult>> = _detectionResultList

    fun setDetectionResultList(detectionResult: ArrayList<MLFoodModel.DetectionResult>){
        _detectionResultList.value = detectionResult
    }
}