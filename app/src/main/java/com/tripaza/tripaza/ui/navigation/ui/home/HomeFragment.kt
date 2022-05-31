package com.tripaza.tripaza.ui.navigation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.tripaza.tripaza.databinding.FragmentHomeBinding
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.HomeListAdapter
import com.tripaza.tripaza.ui.navigation.ui.home.recycler.ListItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeListAdapter: HomeListAdapter
    
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val data = arrayListOf<ListItem>()
        data.add(ListItem("OFFSET", "OFFSET"))
        data.add(ListItem("a", "My A"))
        data.add(ListItem("b", "My B"))
        data.add(ListItem("c", "My C"))
        data.add(ListItem("d", "My D"))
        data.add(ListItem("e", "My E"))
        data.add(ListItem("f", "My F"))
        data.add(ListItem("g", "My G"))
        data.add(ListItem("h", "My H"))
        data.add(ListItem("i", "My I"))
        data.add(ListItem("j", "My J"))
        data.add(ListItem("k", "My K"))
        data.add(ListItem("l", "My L"))
//        binding.frHomeRvHomeList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        
        homeListAdapter = HomeListAdapter(data)
        showStoryRecyclerList()
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun showStoryRecyclerList(){
//        binding.storiesSwipeRefreshContainer.setOnRefreshListener {
//            if (!isUpdating){
//                refreshList()
//            }else{
//                binding.storiesSwipeRefreshContainer.isRefreshing = false
//            }
//        }

//        binding.frHomeRvHomeList.layoutManager = LinearLayoutManager(requireContext())
        val gridLayoutManager = GridLayoutManager(requireContext(),2)
        gridLayoutManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (homeListAdapter.getItemViewType(position)) {
                    homeListAdapter.HEADER -> 2
                    else -> 1
                }
            }
        })
        binding.frHomeRvHomeList.layoutManager = gridLayoutManager
        
        
        

        homeListAdapter.setOnItemClickCallback(object : HomeListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListItem) {
//                val intent = Intent(requireContext(), DetailStoryActivity::class.java)
//                intent.putExtra(DetailStoryActivity.USER_DETAIL_EXTRA, data)
                if(data.id != "OFFSET"){
                    Toast.makeText(requireContext(), "Item ${data.name} Clicked", Toast.LENGTH_SHORT).show()
                }
//                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@StoriesActivity as Activity).toBundle())
            }
        })
        binding.frHomeRvHomeList.adapter = homeListAdapter
    }
    
    
}