package com.tripaza.tripaza

import android.content.res.Configuration
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.tripaza.tripaza.databinding.FragmentRegisterBinding
import com.tripaza.tripaza.databinding.FragmentRegisterLandingBinding

class RegisterFragmentLanding : Fragment() {
    private lateinit var binding: FragmentRegisterLandingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding = FragmentRegisterLandingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.frRegisterLandingIvFeatured.visibility = View.GONE
        }else{
            binding.frRegisterLandingIvFeatured.visibility = View.VISIBLE
        }
        binding.frRegisterLandingBtnOk.setOnClickListener{
            activity?.finish()
        }
        
        toggleFeatured()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggleFeatured()
    }
    
    private fun toggleFeatured(){
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            binding.frRegisterLandingIvFeatured.visibility = View.GONE
        else
            binding.frRegisterLandingIvFeatured.visibility = View.VISIBLE
    }
}