package com.fyp_miscebook.database

import com.google.gson.annotations.SerializedName

class UserEntity {
    @SerializedName("FirstName")
    var firstname: String? = null

    @SerializedName("MiddleName")
    var middlename: String? = null

    @SerializedName("LastName")
    var lastname: String? = null

    @SerializedName("Email")
    var email: String? = null

    @SerializedName("Username")
    var username: String? = null

    @SerializedName("Password")
    var password: String? = null

    @SerializedName("Address")
    var address: String? = null

    @SerializedName("Mobile")
    var mobile: String? = null
}