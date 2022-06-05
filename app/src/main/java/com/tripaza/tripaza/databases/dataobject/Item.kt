package com.tripaza.tripaza.databases.dataobject

import android.os.Parcelable

abstract class Item: Parcelable{
    
    abstract var id: String
    abstract var name: String
    abstract var location: String
    abstract var description: String
    abstract var rating: Int
    abstract var lat: Double
    abstract var lng: Double
    abstract var image: String
    
}