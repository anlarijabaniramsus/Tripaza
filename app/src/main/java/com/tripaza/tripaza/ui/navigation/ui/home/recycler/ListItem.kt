package com.tripaza.tripaza.ui.navigation.ui.home.recycler

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "story")
@Parcelize
data class ListItem(
//    @PrimaryKey
//    @field:SerializedName("id")
    val id: String,
    
//    @field:SerializedName("photoUrl")
    val name: String? = null,

//    @field:SerializedName("createdAt")
) : Parcelable