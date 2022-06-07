package com.tripaza.tripaza.api.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tripaza.tripaza.api.ApiService
import com.tripaza.tripaza.api.Result
import com.tripaza.tripaza.api.postrequest.PostLogin
import com.tripaza.tripaza.api.postrequest.PostRegister
import com.tripaza.tripaza.api.responses.LoginResponse
import com.tripaza.tripaza.api.responses.RegisterResponse

class UserRepository {
    companion object{
        private const val TAG = "UserRepository"
    }
    
    private lateinit var apiService: ApiService
    
    fun setApiService(apiService: ApiService){
        this.apiService = apiService
    }
    
    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData{
        emit(Result.Loading)
        try {
            Log.d(TAG, "login: executing login")
            val postLogin = PostLogin(email, password)
            val response = apiService.login(postLogin)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "login: FAILED " + e.message.toString())
            emit(Result.Error("Login Failed"))
        }
    }
    
    fun register(email: String, password: String, full_name:String, birth_date:String, phone_number:String): LiveData<Result<RegisterResponse>> = liveData{
        emit(Result.Loading)
        try {
            Log.d(TAG, "login: executing login")
            val postRegister = PostRegister(email, password, full_name, birth_date, phone_number)
            val response = apiService.register(postRegister)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "login: FAILED " + e.message.toString())
            emit(Result.Error("Login Failed"))
        }
    }
    
}