package com.tripaza.tripaza.databases.dataobject

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Food")
@Parcelize
data class Food(
//    @PrimaryKey
//    @field:SerializedName("id")
    val id: String,
    
//    @field:SerializedName("name")
    val name: String? = null,
    val location: String  = "",
    val description: String  = "",
    val rating: Int = 1
) : Parcelable