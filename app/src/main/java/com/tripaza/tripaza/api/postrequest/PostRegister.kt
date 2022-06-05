package com.tripaza.tripaza.api.postrequest

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostRegister(
    @field:SerializedName("email")
    val email: String = "",

    @field:SerializedName("password")
    val password: String = "",

    @field:SerializedName("full_name")
    val full_name: String = "",
    
    @field:SerializedName("birth_date")
    val birth_date: String = "",
    
    @field:SerializedName("phone_number")
    val phone_number: String = "",
):Parcelable
