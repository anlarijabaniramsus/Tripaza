package com.tripaza.tripaza.ui.detail

import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
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
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.FoodListAdapter
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.PlaceListAdapter

class DetailFragment : Fragment(), OnMapReadyCallback {
    private var  _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var foodListAdapter: FoodListAdapter
    private lateinit var viewModel: DetailViewModel
    
    private lateinit var mMap: GoogleMap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.foodList.observe(viewLifecycleOwner){
            foodListAdapter.setFoodList(it)
        }
        foodListAdapter = FoodListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_detail) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        setUpRecyclerView()
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