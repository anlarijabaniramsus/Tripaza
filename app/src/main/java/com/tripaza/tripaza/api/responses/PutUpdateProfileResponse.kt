package com.tripaza.tripaza.api.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PutUpdateProfileResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
) : Parcelable

@Parcelize
data class User(

	@field:SerializedName("fieldCount")
	val fieldCount: Int? = null,

	@field:SerializedName("serverStatus")
	val serverStatus: Int? = null,

	@field:SerializedName("protocol41")
	val protocol41: Boolean? = null,

	@field:SerializedName("changedRows")
	val changedRows: Int? = null,

	@field:SerializedName("affectedRows")
	val affectedRows: Int? = null,

	@field:SerializedName("warningCount")
	val warningCount: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("insertId")
	val insertId: Int? = null
) : Parcelable
