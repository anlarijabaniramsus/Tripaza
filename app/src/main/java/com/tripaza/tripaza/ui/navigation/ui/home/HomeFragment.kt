package com.tripaza.tripaza.ui.navigation.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tripaza.tripaza.databinding.FragmentHomeBinding
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.PlaceListAdapter
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.ui.camera.CameraActivity
import com.tripaza.tripaza.ui.detail.DetailActivity
import java.io.File

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

        ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(Manifest.permission.CAMERA), 19992)

        prepareCameraX()
        return binding.root
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(activity?.baseContext!!, it) == PackageManager.PERMISSION_GRANTED
    }
    private fun prepareCameraX() {
        binding.fabCamera.setOnClickListener {
//            if (!allPermissionsGranted()) {
//                ActivityCompat.requestPermissions(
//                    requireActivity(),
//                    REQUIRED_PERMISSIONS,
//                    REQUEST_CODE_PERMISSIONS
//                )
//
//                Log.d(TAG, "prepareCameraX: HERE")
//            }else{
            val intent = Intent(requireContext(), CameraActivity::class.java)
//                Log.d(TAG, "prepareCameraX: HERE 2")
            startActivity(intent)
//            }

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