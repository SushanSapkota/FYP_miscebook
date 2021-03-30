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
    @SerializedName("_id")
    var id: String? = null,

    @SerializedName("FirstName")
    var firstname: String? = null,

    @SerializedName("MiddleName")
    var middlename: String? = null,

    @SerializedName("LastName")
    var lastname: String? = null,

    @SerializedName("Email")
    var email: String? = null,

    @SerializedName("Username")
    var username: String? = null,

    @SerializedName("Password")
    var password: String? = null,

    @SerializedName("Address")
    var address: String? = null,

    @SerializedName("Mobile")
    var mobile: String? = null
)
