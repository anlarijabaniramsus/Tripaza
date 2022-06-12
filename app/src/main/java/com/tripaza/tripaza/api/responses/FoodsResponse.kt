package com.tripaza.tripaza.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodsResponse(

	@field:SerializedName("foods")
	val foods: List<FoodsItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class FoodsItem(

	@field:SerializedName("food_name")
	val foodName: String? = null,

	@field:SerializedName("restaurant_address")
	val restaurantAddress: String? = null,

	@field:SerializedName("restaurant_name")
	val restaurantName: String? = null
) : Parcelable
