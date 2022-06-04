package com.tripaza.tripaza.ui.registration

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tripaza.tripaza.api.ApiConfig
import retrofit2.Callback
import com.tripaza.tripaza.api.LoginResponse
import com.tripaza.tripaza.api.PostLogin
import retrofit2.Call
import retrofit2.Response

class MainActivityModel: ViewModel() {
    companion object{
        private const val TAG = "MainActivityModel"
    }
    lateinit var SET_DEVELOPMENT_ONLY_CONTEXT: Context
    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError
    
    
    fun login(email: String, password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(PostLogin(email, password))
        client.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    _loginResponse.value = response.body()
                    _isLoading.value = false
                    _isError.value = false
                }else{
                    Log.e(TAG, "onResponse: ERROR" )
                    _isLoading.value = false
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "onResponse: MAJOR FAILURE" )
                _isLoading.value = false
                _isError.value = true
            }
        })
        
    }
    
    
    
    
}