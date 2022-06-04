package com.tripaza.tripaza.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostLogin(

    @field:SerializedName("email")
    val status: String = "",

    @field:SerializedName("password")
    val user: String = "",
    
):Parcelable
