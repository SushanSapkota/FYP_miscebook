package com.fyp_miscebook.model

import com.google.gson.annotations.SerializedName

data class TopVenueResponse(

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: Long? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("image")
	val image: String? = null
)
