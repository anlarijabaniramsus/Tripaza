package com.tripaza.tripaza.api.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.tripaza.tripaza.api.ApiService
import com.tripaza.tripaza.api.Result
import com.tripaza.tripaza.api.postrequest.PostLogin
import com.tripaza.tripaza.api.postrequest.PostRegister
import com.tripaza.tripaza.api.responses.LoginResponse
import com.tripaza.tripaza.api.responses.MapNearbyResponse
import com.tripaza.tripaza.api.responses.RegisterResponse

class MapRepository {
    companion object{
        private const val TAG = "UserRepository"
    }

    private lateinit var apiService: ApiService

    fun setApiService(apiService: ApiService){
        this.apiService = apiService
    }

    fun mapNearbySearch(keyword: String,  location: String, radius: Int): LiveData<Result<MapNearbyResponse>> = liveData{
        emit(Result.Loading)
        try {
            Log.d(TAG, "mapNearbySearch: executing mapNearbySearch")
            val response = apiService.mapNearbySearch(keyword, location, radius)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d(TAG, "mapNearbySearch: FAILED " + e.message.toString())
            emit(Result.Error("mapNearbySearch Failed"))
        }
    }
}



