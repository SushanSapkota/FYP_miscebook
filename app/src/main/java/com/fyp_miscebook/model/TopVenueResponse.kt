package com.fyp_miscebook.model

import com.google.gson.annotations.SerializedName

data class TopVenueResponse(

	@field:SerializedName("ID")
	val futsal_id: String? = null,

	@field:SerializedName("Name")
	val name: String? = null,

	@field:SerializedName("Address")
	val address: String? = null,

	@field:SerializedName("Email")
	val email: String? = null,

	@field:SerializedName("Contact")
	val contact: String? = null,

	@field:SerializedName("Image")
	val image: String? = null
)
