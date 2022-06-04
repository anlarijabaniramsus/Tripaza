package com.tripaza.tripaza.databases.dataobject

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

//@Entity(tableName = "Place")
@Parcelize
data class Place(
    val id: String,
    val name: String = "",
    val location: String  = "",
    val description: String  = "",
    val rating: Int = 1,
    val lat: Double = 0.0,
    val lng: Double = 0.0
) : Parcelable