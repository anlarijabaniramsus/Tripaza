package com.tripaza.tripaza.api.postrequest

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class PostGetUserProfile(

    @field:SerializedName("id")
    val id: String = "",

): Parcelable
