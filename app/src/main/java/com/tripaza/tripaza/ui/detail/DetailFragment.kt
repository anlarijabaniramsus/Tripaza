package com.tripaza.tripaza.ui.detail

import android.content.ClipDescription
import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.databinding.FragmentDetailBinding
import com.tripaza.tripaza.helper.StarRatingHelper
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.FoodListAdapter
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.PlaceListAdapter

class DetailFragment : Fragment(), OnMapReadyCallback {
    companion object{
        private val ITEM_TITLE = "item_title"
        private val ITEM_DESCRIPTION = "item_description"
        private val ITEM_RATING = "item_rating"
        private val ITEM_LAT = "item_lat"
        private val ITEM_LNG = "item_lng"
        private const val TAG = "DetailFragment"
        @JvmStatic
        fun newInstance(title: String,
                        description: String,
                        rating: Int,
                        lat: Double,
                        lng: Double
        ) = DetailFragment().apply {
            arguments = Bundle().apply {
                putString(ITEM_TITLE, title)
                putString(ITEM_DESCRIPTION, description)
                putInt(ITEM_RATING, rating)
                putDouble(ITEM_LAT, lat)
                putDouble(ITEM_LNG, lng)
            }
        }
    }
    
    private var  _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var foodListAdapter: FoodListAdapter
    private lateinit var viewModel: DetailViewModel
    
    private lateinit var mMap: GoogleMap
    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        arguments?.let {
            val title = it.getString(ITEM_TITLE, "")
            val description = it.getString(ITEM_DESCRIPTION, "")
            val rating = it.getInt(ITEM_RATING, 0)
            val lat = it.getDouble(ITEM_LAT, 0.0)
            val lng = it.getDouble(ITEM_LNG, 0.0)
            viewModel.setTitle(title)
            viewModel.setDescription(description)
            viewModel.setRating(rating)
            viewModel.setLat(lat)
            viewModel.setLng(lng)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        
        viewModel.foodList.observe(viewLifecycleOwner){
            foodListAdapter.setFoodList(it)
        }
        foodListAdapter = FoodListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val bundle = arguments?.getBundle(DetailActivity.EXTRA_BUNDLE)
        val place = bundle?.getParcelable<Place>(DetailActivity.EXTRA_DATA)
        Log.d(TAG, "onViewCreated: ${place?.name}" )
        
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_detail) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        setUpRecyclerView()
        
        viewModel.title.observe(viewLifecycleOwner){
            binding.title.text = it
        }
        viewModel.description.observe(viewLifecycleOwner){
            binding.description.text = it
        }
        viewModel.rating.observe(viewLifecycleOwner){
            StarRatingHelper.setStarRating(binding.starRating, it)
        }
    }

    override fun onMapReady(mMap: GoogleMap) {
        this.mMap = mMap
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
    }
    private fun setUpRecyclerView(){
        binding.rvYouMayAlsoLike.layoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        
        foodListAdapter.setOnItemClickCallback(object : FoodListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Food) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                startActivity(intent)
            }
        })
        binding.rvYouMayAlsoLike.adapter = foodListAdapter
    }
    
}