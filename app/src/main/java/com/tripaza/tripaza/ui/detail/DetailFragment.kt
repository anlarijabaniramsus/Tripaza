package com.tripaza.tripaza.ui.detail

import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.tripaza.tripaza.R
import com.tripaza.tripaza.api.Result
import com.tripaza.tripaza.api.responses.FoodsItem
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.databinding.FragmentDetailBinding
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.helper.MapHelper
import com.tripaza.tripaza.helper.StarRatingHelper
import com.tripaza.tripaza.ui.navigation.ui.home.HomeFragment
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.FoodListAdapter

class DetailFragment : Fragment(), OnMapReadyCallback {
    private lateinit var selectedMarker: Marker
    companion object{
        private const val ITEM = "item"
        private const val TAG = "DetailFragment"
        @JvmStatic
        fun newInstance(
            item: Item
        ) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ITEM, item)
            }
        }
    }
    
    private var  _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var foodListAdapter: FoodListAdapter
    private lateinit var viewModel: DetailViewModel
    private var foodList = ArrayList<Food>()
    private lateinit var mMap: GoogleMap
    


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        arguments?.let {
            val item = it.getParcelable<Food>(ITEM)
            viewModel.setItem(item!!)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        binding.mapParentLayout.clipToOutline = true
        val bundle = arguments?.getBundle(DetailActivity.EXTRA_BUNDLE)
        val place = bundle?.getParcelable<Place>(DetailActivity.EXTRA_DATA)
        Log.d(TAG, "onViewCreated: ${place?.name}" )
        
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_detail) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        viewModel.retrieveFoodList().observe(viewLifecycleOwner){
                when(it) {
                    is Result.Error -> {
                        Log.d(TAG, "retrieveFoodList: ERROR")
                    }
                    is Result.Loading -> {
                        Log.d(TAG, "retrieveFoodList: LOADING")
                    }
                    is Result.Success -> {
                        Log.d(TAG, "retrieveFoodList: SUCCESS")
                        val nFoodList = arrayListOf<Food>()
                        var dummyId = 1
                        val foodItem = it.data.foods as ArrayList<FoodsItem>
                        Log.d(TAG, "retrieveFoodList: SUCCESS retrievedArrayListSize: ${it.data.foods.size}")
                        for (i in foodItem) {
                            val item = Food(
                                "ID_${dummyId++}",
                                i.foodName.toString(),
                                i.restaurantAddress.toString(),
                                "",
                                i.rating?.toInt()?:0,
                                i.latitude?.toDouble()?:0.0,
                                i.longitude?.toDouble()?:0.0,
                                i.imageUrl.toString(),
                            )
                            nFoodList.add(item)
                        }
                        viewModel.setFoodList(nFoodList)
                        setUpRecyclerView()
                    }
                }
        }
        
        viewModel.foodList.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: OBSERVE foodlist: ${it.size}")
            foodList = it
            setUpRecyclerView()
        }
        
        viewModel.item.observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: OBSERVE item: ${it.name}")
            binding.title.text = it.name
            binding.description.text = it.description
            StarRatingHelper.setStarRating(binding.starRating, it.rating)
            Log.d(TAG, "onViewCreated: ${it.image}")
            HelperTools.glideLoaderRounded(requireContext(), it.image, binding.ivItemImage)
        }
    }

    override fun onMapReady(mMap: GoogleMap) {
        Log.d(TAG, "onMapReady")
        this.mMap = mMap
        
        
        val markerOptions = MarkerOptions()
        markerOptions.visible(false)
        markerOptions.position(LatLng(0.0, 0.0))
        selectedMarker = mMap.addMarker(markerOptions)!!
        
        viewModel.item.observe(this){
            selectedMarker.position = LatLng(it.lat, it.lng)
            selectedMarker.title = it.name
            selectedMarker.isVisible = true
            MapHelper.moveCamera(mMap, LatLng(it.lat, it.lng))
        }
        
    }

    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
    }
    private fun setUpRecyclerView(){
        Log.d(TAG, "setUpRecyclerView")
        foodListAdapter = FoodListAdapter()
        foodListAdapter.setFoodList(foodList)
        binding.rvYouMayAlsoLike.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        
        foodListAdapter.setOnItemClickCallback(object : FoodListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Food) {
                val bundle = Bundle()
                bundle.putParcelable(DetailActivity.EXTRA_DATA, data)
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_BUNDLE, bundle)
                startActivity(intent)
            }
        })
        binding.rvYouMayAlsoLike.adapter = foodListAdapter
    }
    
}