package com.tripaza.tripaza.api

import com.tripaza.tripaza.api.postrequest.PostLogin
import com.tripaza.tripaza.api.postrequest.PostRegister
import com.tripaza.tripaza.api.responses.LoginResponse
import com.tripaza.tripaza.api.responses.RegisterResponse
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
}