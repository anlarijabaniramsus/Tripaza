package com.tripaza.tripaza.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginResponse(

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("user")
    val user: Int? = null,
    
    @field:SerializedName("message")
    val message: String? = null
):Parcelable
