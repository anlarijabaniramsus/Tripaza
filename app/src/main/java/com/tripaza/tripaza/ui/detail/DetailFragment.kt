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

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.Item
import com.tripaza.tripaza.databases.dataobject.Place
import com.tripaza.tripaza.databinding.FragmentDetailBinding
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.helper.StarRatingHelper
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.FoodListAdapter

class DetailFragment : Fragment(), OnMapReadyCallback {
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
    
    private lateinit var mMap: GoogleMap
    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        arguments?.let {
            val item = it.getParcelable<Item>(ITEM)
            viewModel.setItem(item!!)
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
        
        viewModel.item.observe(viewLifecycleOwner){
            binding.title.text = it.name
            binding.description.text = it.description
            StarRatingHelper.setStarRating(binding.starRating, it.rating)
            binding.ivItemImage
            HelperTools.glideLoader(requireContext(), it.image, binding.ivItemImage, false)
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