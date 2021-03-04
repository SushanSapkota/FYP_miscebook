package com.fyp_miscebook.model

import com.google.gson.annotations.SerializedName

data class Futsal(

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("phone")
	val phone: Long? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("pitch_condition")
	val pitchCondition: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("venue_condition")
	val venueCondition: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
