package com.tripaza.tripaza.databases.dataobject

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Place")
@Parcelize
data class Place(
    val id: String,
    val name: String? = null,
    val location: String  = "",
    val description: String  = "",
    val rating: Int = 1
) : Parcelable