package com.tripaza.tripaza.ui.navigation.ui.home

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tripaza.tripaza.databinding.FragmentHomeBinding
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.PlaceListAdapter
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.ui.camera.DetectResultActivity
import com.tripaza.tripaza.ui.detail.DetailActivity
import java.io.*

class HomeFragment : Fragment() {
    companion object{
        const val CAMERA_X_RESULT_CODE = 199
        private const val REQUEST_CODE_PERMISSIONS = 1994
        private const val TAG = "HomeFragment"
    }
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var placeListAdapter: PlaceListAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        placeListAdapter = PlaceListAdapter()
        homeViewModel.foodList.observe(viewLifecycleOwner){
            placeListAdapter.setFoodList(it)
        }
        homeViewModel.placeList.observe(viewLifecycleOwner){
            placeListAdapter.setPlaceList(it)
            showStoryRecyclerList()
        }
        homeViewModel.featuredPlace.observe(viewLifecycleOwner){
            placeListAdapter.setFeaturedItem(it)
        }

//        ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(Manifest.permission.CAMERA), 19992)

        prepareCameraBtn()
        return binding.root
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(activity?.baseContext!!, it) == PackageManager.PERMISSION_GRANTED
    }
    private fun prepareCameraBtn() {
        binding.fabCamera.setOnClickListener {
            if (!allPermissionsGranted()) {
                Toast.makeText(requireContext(), "Permission Required", Toast.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    REQUIRED_PERMISSIONS,
                    12473
                )
            }else{
                startDialog()
            }
        }
    }
    
    private fun startDetectResultActivity(bitmap: Bitmap){
        Log.d(TAG, "StartDetectResultActivity")
        val intent = Intent(requireContext(), DetectResultActivity::class.java)
        val fileUri = createTemporaryFile(bitmap)
        Log.d(TAG, "Uri: $fileUri")
        intent.putExtra(DetectResultActivity.IMAGE_FILE_URI, fileUri.toString())
        startActivity(intent)
    }

    private fun createTemporaryFile(bitmap: Bitmap): Uri {
        Log.d(TAG, "createTemporaryFile: started")
        val directory: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tempFile = File.createTempFile("tempFile", ".jpg", directory)
        try {
            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,100 , byteArrayOutputStream)
            val outputStream: OutputStream = FileOutputStream(tempFile)
            outputStream.write(byteArrayOutputStream.toByteArray())
            outputStream.close()
        }catch (e: Exception){
            Log.e(TAG, "createTemporaryFile: FAILED", )
        }
        Log.d(TAG, "createTemporaryFile: returned uri: ${tempFile.toUri()}")
        return tempFile.toUri()
    }
    
    private fun startDialog() {
        val myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        myAlertDialog.setTitle("Food Detection")
        myAlertDialog.setMessage("How do you want to get your picture?")
        myAlertDialog.setPositiveButton("Gallery") { arg0, arg1 ->
            startGallery()
        }
        myAlertDialog.setNegativeButton("Camera") { arg0, arg1 ->
            
//            startActivityForResult(intent, 333)
            startCamera()
        }
        myAlertDialog.show()
    }
    private fun startCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcherIntentCamera.launch(intent)
    }
    
    private val launcherIntentCamera = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d(TAG, "launcherIntentGallery: ")
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            var image = result.data?.extras?.get("data") as Bitmap
            startDetectResultActivity(image)
        }
    }
    
    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }
    
    private val launcherIntentGallery = registerForActivityResult( ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d(TAG, "launcherIntentGallery: ")
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImgUri: Uri = result.data?.data as Uri
            try{
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedImgUri)
                startDetectResultActivity(bitmap)
                Log.d(TAG, "launcherIntentGallery: try")
            }catch (e: Exception){
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "launcherIntentGallery: bitmap", e)
            }
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showStoryRecyclerList(){
        val gridLayoutManager = GridLayoutManager(requireContext(),2)
        gridLayoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (placeListAdapter.getItemViewType(position)) {
                    PlaceListAdapter.HEADER -> 2
                    else -> 1
                }
            }
        })
        binding.frHomeRvHomeList.layoutManager = gridLayoutManager
        placeListAdapter.setOnItemClickCallback(object : PlaceListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Place) {
                if(data.id != "OFFSET"){
                    val bundle = Bundle()
                    bundle.putParcelable(DetailActivity.EXTRA_DATA, data)
                    val intent = Intent(requireContext(), DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_BUNDLE, bundle)
                    startActivity(intent)
                }
            }
        })
        binding.frHomeRvHomeList.adapter = placeListAdapter
    }
}