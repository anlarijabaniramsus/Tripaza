package com.tripaza.tripaza.ui.navigation.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.FragmentProfileBinding
import com.tripaza.tripaza.ui.preferences.PreferencesActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textProfile
//        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        binding.profileIvEditAccountButton.setOnClickListener {
            val intent = Intent(context, ProfileEditActivity::class.java)
            startActivity(intent)
        }

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(binding.root.context)
            .load(R.drawable.profile_images)
            .circleCrop()
//            .placeholder(ContextCompat.getDrawable(binding.root.context,R.drawable.abc))
            .apply(requestOptions)
            .into(binding.frProfileIvProfilePhoto)
        
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.frProfileLinlaySettingItem.setOnClickListener{
            val intent = Intent(context, PreferencesActivity::class.java)
            startActivity(intent)
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}