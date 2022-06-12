package com.tripaza.tripaza.ui.navigation.ui.profile

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tripaza.tripaza.R
import com.tripaza.tripaza.api.Result
import com.tripaza.tripaza.api.responses.FoodsItem
import com.tripaza.tripaza.databases.dataobject.Food
import com.tripaza.tripaza.databases.dataobject.User
import com.tripaza.tripaza.databinding.FragmentProfileBinding
import com.tripaza.tripaza.helper.Constants.DUMMY_IMAGE_PROFILE
import com.tripaza.tripaza.helper.HelperTools
import com.tripaza.tripaza.helper.PreferencesHelper
import com.tripaza.tripaza.ui.detail.DetailFragment
import com.tripaza.tripaza.ui.preferences.PreferencesActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: PreferencesHelper
    private lateinit var viewModel: ProfileViewModel
    private lateinit var user: User
    companion object{
        private const val TAG = "ProfileFragment"
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        preferences = PreferencesHelper(requireContext())
        user = preferences.getUser()
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        
        binding.profileIvEditAccountButton.setOnClickListener {
            val intent = Intent(context, ProfileEditActivity::class.java)
            intent.putExtra(ProfileEditActivity.EXTRA_USER_DATA, user)
            startActivity(intent)
        }
        
        HelperTools.glideLoaderCircle(binding.root.context, DUMMY_IMAGE_PROFILE, binding.frProfileIvProfilePhoto)
        viewModel.user.observe(viewLifecycleOwner){
            user = it
            Log.d(TAG, "onCreateView: ${user}")
            updateUi()
        }
        
        viewModel.getUserProfileData(user.id).observe(viewLifecycleOwner){
            Log.d(TAG, "onViewCreated: viewModel.getUserProfileData(user.id)")
            when(it) {
                is Result.Error -> {
                    Log.d(TAG, "retrieveFoodList: ERROR")
                }
                is Result.Loading -> {
                    Log.d(TAG, "retrieveFoodList: LOADING")
                }
                is Result.Success -> {
                    if (it.data.status == true){
                        val user = User(
                            user.id,
                            it.data.data?.fullName.toString(),
                            it.data.data?.birthday.toString().substring(0..9),
                            it.data.data?.phoneNumber.toString(),
                            it.data.data?.email.toString()
                        )
                        Log.d(TAG, "onCreateView: FRESH FETCH USER: ${user}")
                        Log.d(TAG, "onViewCreated: new USER DATA: ${user}")
                        preferences.setUser(user)
                        viewModel.setUser(user)
                        updateUi()
                    }
                }
            }
        }
        
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.frProfileLinlaySettingItem.setOnClickListener{
            val intent = Intent(context, PreferencesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUi() {
        Log.d(TAG, "updateUi")
        binding.frProfileTvValueName.text = user.name
        binding.frProfileTvValueDob.text = user.dob
        binding.frProfileTvValuePhone.text = user.phone
        binding.frProfileTvValueEmail.text = user.email
        binding.frProfileTvValuePassword.text = "****"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}