package com.tripaza.tripaza.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//    @FormUrlEncoded
    @POST("login")
    fun login(
        @Body() postLogin: PostLogin
    ): Call<LoginResponse>

//    @FormUrlEncoded
//    @POST("register")
//    fun register(
//        @Field("email") name: String,
//        @Field("password") job: String
//    ): Call<RegisterResponse>
    
}