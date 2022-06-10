package com.tripaza.tripaza.api

import com.tripaza.tripaza.api.postrequest.PostLogin
import com.tripaza.tripaza.api.postrequest.PostRegister
import com.tripaza.tripaza.api.responses.LoginResponse
import com.tripaza.tripaza.api.responses.MapNearbyResponse
import com.tripaza.tripaza.api.responses.RegisterResponse
import com.tripaza.tripaza.helper.Constants.MAP_API_KEY
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Body() postLogin: PostLogin
    ): LoginResponse

    @POST("register")
    suspend fun register(
        @Body() postRegister: PostRegister
    ): RegisterResponse
    
//    @FormUrlEncoded
    @POST("maps/api/place/nearbysearch/json")
    suspend fun mapNearbySearch(
        @Query("keyword") keyword: String,
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "restaurant",
        @Query("key") key: String = MAP_API_KEY
    ): MapNearbyResponse
}