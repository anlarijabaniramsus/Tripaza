package com.tripaza.tripaza.databases.dataobject

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    
    val id: String = "",
    val name: String = "",
    val dob: String = "",
    val phone: String = "",
    val email: String = "",
    
) : Parcelable