package com.tripaza.tripaza.ui.navigation.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.tripaza.tripaza.api.Result
import com.tripaza.tripaza.databases.dataobject.User
import com.tripaza.tripaza.databinding.ActivityProfileEditBinding
import com.tripaza.tripaza.helper.PreferencesHelper
import com.tripaza.tripaza.helper.Validator

class ProfileEditActivity : AppCompatActivity() {
    private lateinit var viewModel: ProfileViewModel
    private lateinit var preferences: PreferencesHelper
    private lateinit var user: User
    private lateinit var binding: ActivityProfileEditBinding
    private var isExecutingRegistration = false
    
    companion object{
        private const val TAG = "ProfileEditActivity"
        const val EXTRA_USER_DATA = "extra_user_data_edit"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        preferences = PreferencesHelper(this)
        //        user = preferences.getUser()
        val x = intent.getParcelableExtra<User>(EXTRA_USER_DATA)
        if (x != null){
            user = x
        }
        

        Log.d(TAG, "onCreate: ${user}")
        updateUi()
//        viewModel.getUserProfileData(user.id).observe(this){
//            when(it) {
//                is Result.Error -> {
//                    binding.progressBar.visibility = View.GONE
//                    Log.d(TAG, "getUserProfileData: ERROR")
//                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show()
//                }
//                is Result.Loading -> {
//                    Log.d(TAG, "getUserProfileData: LOADING")
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//                is Result.Success -> {
//                    if (it.data.status == true){
//                        binding.progressBar.visibility = View.GONE
//                        val user = User(
//                            user.id,
//                            it.data.data?.fullName.toString(),
//                            it.data.data?.birthday.toString(),
//                            it.data.data?.phoneNumber.toString(),
//                            it.data.data?.email.toString()
//                        )
//                        Log.d(TAG, "pnCreate: new USER DATA: ${user}")
//                        preferences.setUser(user)
//                        viewModel.setUser(user)
//                        updateUi()
//                    }else{
//                        Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show()
//                        finish()
//                    }
//                }
//            }
//        }
        
        binding.profileEditBtnUpdate.setOnClickListener{
            if (isExecutingRegistration){
                Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show()
            }else{
                performUpdateDataValidation()
            }
        }
        
    }
    fun updateUi(){
        Log.d(TAG, "updateUi: ${user.toString()}")
        binding.profileEditEtName.setText(user.name)
        binding.profileEditEtDob.setText(user.dob)
        binding.profileEditEtEmail.setText(user.email)
        binding.profileEditEtPhone.setText(user.phone)
        binding.profileEditEtPassword.setText("")
        
    }
    private fun performUpdateDataValidation() {
        val name = binding.profileEditEtName.text.toString()
        val dob = binding.profileEditEtDob.text.toString()
        val email = binding.profileEditEtEmail.text.toString()
        val phone = binding.profileEditEtPhone.text.toString()
        val password = binding.profileEditEtPassword.text.toString()
        Log.d(TAG, "performUpdateDataValidation: $name, $dob, $email, $phone, $password")
        var allowUpdateProcess = true
        var result = Validator.isInputValid(name)
        if (allowUpdateProcess && !result.valid){
            Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.profileEditEtName.requestFocus()
            binding.profileEditTilName.error = result.errorMessage
            allowUpdateProcess = false
        }

        result = Validator.isInputValid(dob)
        if (allowUpdateProcess && !result.valid){
            Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.profileEditEtDob.requestFocus()
            binding.profileEditEtDob.setText("")
            allowUpdateProcess = false
        }

        result = Validator.isEmailValid(email)
        if (allowUpdateProcess && !result.valid){
            Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.profileEditEtEmail.requestFocus()
            binding.profileEditTilEmail.error = result.errorMessage
            allowUpdateProcess = false
        }

        result = Validator.isPhoneValid(phone)
        if (allowUpdateProcess && !result.valid){
            Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.profileEditEtPhone.requestFocus()
            binding.profileEditTilPhone.error = result.errorMessage
            allowUpdateProcess = false
        }

        result = Validator.isPasswordValid(password)
        if (allowUpdateProcess && !result.valid){
            Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
            binding.profileEditEtPassword.requestFocus()
            binding.profileEditTilPassword.error = result.errorMessage
            allowUpdateProcess = false
        }
    
        if (allowUpdateProcess){
            viewModel.updateUserProfileData(user.id.toInt(), name, dob, phone, email, password).observe(this){
                Toast.makeText(this, "EXECUTING UPDATE PROCESS", Toast.LENGTH_SHORT).show()
                isExecutingRegistration = true
                when (it) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Error -> {
                        isExecutingRegistration = false
                        binding.progressBar.visibility = View.GONE
                        binding.profileEditTvError.text = "Update Failed"
                    }
                    is Result.Success -> {
                        isExecutingRegistration = false
                        binding.profileEditTvError.text = ""
                        binding.progressBar.visibility = View.GONE
                        if (it.data.message == "Succes Update Data"){
                            finish()
                        }else{
                            binding.profileEditTvError.text = "Update Failed"
                        }
                    }
                }
            }
        }
    }
}