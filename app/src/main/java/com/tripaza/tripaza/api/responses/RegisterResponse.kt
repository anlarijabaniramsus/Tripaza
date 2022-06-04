package com.tripaza.tripaza.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterResponse(

    @field:SerializedName("status")
    val status: Boolean = false,

    @field:SerializedName("message")
    val message: String = ""
):Parcelable
