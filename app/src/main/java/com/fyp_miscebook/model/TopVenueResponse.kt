package com.fyp_miscebook.model

import com.google.gson.annotations.SerializedName

data class TopVenueResponse(

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("Futsal_ID")
	val id: String? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("StartTime")
	val starttime: Long? = null,

	@field:SerializedName("EndTime")
	val endtime: String? = null,

	@field:SerializedName("BookDate")
	val bookdate: String? = null,

	@field:SerializedName("NumberOfPlayer")
	val numberofplayer: String? = null,

	@field:SerializedName("Image")
	val image: String? = null
)
