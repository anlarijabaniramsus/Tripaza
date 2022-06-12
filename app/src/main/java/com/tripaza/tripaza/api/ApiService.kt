package com.tripaza.tripaza.api

import com.tripaza.tripaza.api.postrequest.PostGetUserProfile
import com.tripaza.tripaza.api.postrequest.PostLogin
import com.tripaza.tripaza.api.postrequest.PostRegister
import com.tripaza.tripaza.api.postrequest.PutUpdateProfile
import com.tripaza.tripaza.api.responses.*
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
    
    @POST("/")
    suspend fun getFoodList(): FoodsResponse
    
    @POST("maps/api/place/nearbysearch/json")
    suspend fun mapNearbySearch(
        @Query("keyword") keyword: String,
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String = "restaurant,bakery,liquor_store,meal_delivery,meal_takeaway,food,cafe",
        @Query("key") key: String = MAP_API_KEY
    ): MapNearbyResponse
    
    @PUT("user/edit")
    suspend fun updateUserProfile(
        @Body() putUpdateProfile: PutUpdateProfile
    ):PutUpdateProfileResponse
    
    @POST("user")
    suspend fun getUserProfileData(
        @Body() postGetUserProfile: PostGetUserProfile
    ):ProfileDataResponse
}