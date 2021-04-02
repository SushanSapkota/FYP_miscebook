package com.fyp_miscebook.model

import com.google.gson.annotations.SerializedName

data class BookingResponse(

	@field:SerializedName("_id")
	val _id: String? = null,

	@field:SerializedName("Futsal ID")
	val futsal_id: String? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("Start")
	val starttime: Int? = null,

	@field:SerializedName("End")
	val endtime: Int? = null,

	@field:SerializedName("BookDate")
	val bookdate: Int? = null,

	@field:SerializedName("NumberOfPlayer")
	val numberofplayer: String? = null,

	@field:SerializedName("Image")
	val image: String? = null
)
