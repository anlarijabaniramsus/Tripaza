package com.tripaza.tripaza.databases.dataobject

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

//@Entity(tableName = "Food")
@Parcelize
data class Food(
    val id: String,
    val name: String? = "",
    val location: String  = "",
    val description: String  = "",
    val rating: Int = 0,
    val lat: Double = 0.0,
    val lng: Double =0.0
) : Parcelable