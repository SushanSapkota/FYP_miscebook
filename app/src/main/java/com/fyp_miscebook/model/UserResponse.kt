package com.fyp_miscebook.model

import com.google.gson.annotations.SerializedName

data class UserResponse(

    /*  "id": "60406cb09a00131100002f29",
  "first_name": "demo",
  "middle_name": "demo",
  "last_name": "demo",
  "username": "username",
  "email": "demo@gmail.com"
  "image": "demo"*/
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("first_name")
    val firstName: String? = null,

    @field:SerializedName("middle_name")
    val middleName: String? = null,

    @field:SerializedName("last_name")
    val lastName: String? = null,

    @field:SerializedName("username")
    val userName: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("image")
    val image: String? = null

)
