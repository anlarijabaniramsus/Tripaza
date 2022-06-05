package com.tripaza.tripaza.api.postrequest

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostLogin(

    @field:SerializedName("email")
    val status: String = "",

    @field:SerializedName("password")
    val password: String = "",
    
):Parcelable
