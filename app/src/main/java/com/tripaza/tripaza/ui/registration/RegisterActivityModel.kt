package com.tripaza.tripaza.ui.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.api.ApiConfig
import com.tripaza.tripaza.api.Injection
import retrofit2.Callback
import com.tripaza.tripaza.api.responses.RegisterResponse
import com.tripaza.tripaza.api.postrequest.PostRegister
import retrofit2.Call
import retrofit2.Response

class RegisterActivityModel: ViewModel() {
    private var userRepository =  Injection.provideUserRepository()
    fun register(email: String, password: String, full_name: String, birth_date: String, phone_number: String)
        = userRepository.register(email, password, full_name, birth_date, phone_number)
}