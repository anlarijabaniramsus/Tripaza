package com.tripaza.tripaza.ui.camera

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tripaza.tripaza.databinding.ActivityDetectResultBinding
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.ml.MLFoodModel
import com.tripaza.tripaza.ui.camera.recycler.DetectResultAdapter
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.PlaceListAdapter
import org.tensorflow.lite.support.image.TensorImage

class DetectResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetectResultBinding
    private lateinit var viewModel: DetectResultActivityViewModel
    private lateinit var detectResultAdapter: DetectResultAdapter
    companion object{
        private const val TAG = "DetectResultActivity"
        const val IMAGE_FILE_URI = "image_file_uri"
    }
//    private val REQUIRED_PERMISSIONS = arrayOf(
//        Manifest.permission.CAMERA,
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.ACCESS_FINE_LOCATION
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        binding = ActivityDetectResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detectResultAdapter = DetectResultAdapter()
        viewModel = ViewModelProvider(this).get(DetectResultActivityViewModel::class.java)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Search"
        val imageUri = Uri.parse(intent.getStringExtra(IMAGE_FILE_URI))
        HelperTools.glideLoaderCircle(this,imageUri.toString(), binding.ivTakenPhoto)
        viewModel.setPhotoBitmap(getBitmapFromUri(imageUri))
        viewModel.photoBitmap.observe(this){
            if (it != null){
                binding.ivTakenPhoto.setImageBitmap(it)
                classifyImage(it)
            }
        }
        viewModel.detectionResultList.observe(this){
            detectResultAdapter.setDetectionResultList(it)
            showStoryRecyclerList()
        }
    }
    
    private fun getBitmapFromUri(uri: Uri): Bitmap{
        return MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
    }

    private fun classifyImage(image: Bitmap){
        val model = MLFoodModel.newInstance(this)
        val tensorImage = TensorImage.fromBitmap(image)

        // Runs model inference and gets result.
        val outputs = model.process(tensorImage)
        val detectionResult = outputs.detectionResultList[0]

        // Gets result from DetectionResult.
        val location = detectionResult.locationAsRectF
        val category = detectionResult.categoryAsString
        val score = detectionResult.categoryAsString
        Log.d(TAG, "classifyImage: LOCATION: " + location)
        Log.d(TAG, "classifyImage: CATEGORY: " + category)
        Log.d(TAG, "classifyImage: SCORE: " + score)

        viewModel.setDetectionResultList(outputs.detectionResultList as ArrayList<MLFoodModel.DetectionResult>)
        model.close()
    }


    private fun showStoryRecyclerList(){
        val gridLayoutManager = GridLayoutManager(this,2)
        gridLayoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (detectResultAdapter.getItemViewType(position)) {
                    PlaceListAdapter.HEADER -> 2
                    else -> 1
                }
            }
        })
        
        binding.rvDetectResult.layoutManager = gridLayoutManager
        detectResultAdapter.setOnItemClickCallback(object : DetectResultAdapter.OnItemClickCallback{
            override fun onItemClicked(data: MLFoodModel.DetectionResult) {
//                val bundle = Bundle()
//                bundle.putParcelable(DetailActivity.EXTRA_DATA, data)
//                val intent = Intent(this, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.EXTRA_BUNDLE, bundle)
//                startActivity(intent)
                Toast.makeText(baseContext, "ITEM CLICKED", Toast.LENGTH_SHORT).show()
            }
        })
        binding.rvDetectResult.adapter = detectResultAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
     
     
}