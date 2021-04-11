package com.fyp_miscebook.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("Email")
    val email: String? = null,

    @field:SerializedName("Address")
    val address: String? = null,

    @field:SerializedName("Username")
    val username: String? = null,

    @field:SerializedName("FirstName")
    val firstName: String? = null,

    @field:SerializedName("admin")
    val admin: Boolean? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("LastName")
    val lastName: String? = null,

    @field:SerializedName("MiddleName")
    val middleName: String? = null,

    @field:SerializedName("Mobile")
    val mobile: String? = null,

    @field:SerializedName("Password")
    val password: String? = null
)