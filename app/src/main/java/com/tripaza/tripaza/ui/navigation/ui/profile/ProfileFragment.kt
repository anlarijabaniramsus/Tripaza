package com.tripaza.tripaza.ui.navigation.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.tripaza.tripaza.R
import com.tripaza.tripaza.databinding.FragmentProfileBinding
import com.tripaza.tripaza.ui.preferences.PreferencesActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        binding.frProfileLinlayNameItem.setOnClickListener {
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