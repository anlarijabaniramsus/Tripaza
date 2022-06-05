package com.tripaza.tripaza.databases.dataobject

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    
    override var id: String,
    override var name: String = "",
    override var location: String  = "",
    override var description: String  = "",
    override var rating: Int = 0,
    override var lat: Double = 0.0,
    override var lng: Double = 0.0,
    override var image: String
    
) : Parcelable, Item()

