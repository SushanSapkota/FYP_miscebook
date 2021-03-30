package com.fyp_miscebook.database

import com.google.gson.annotations.SerializedName

class BookingEntity {

    @field:SerializedName("Futsal_ID")
    var futsal_id: String? = null

    @field:SerializedName("Name")
    var name: String? = null

    @field:SerializedName("Address")
    var address: String? = null

    @field:SerializedName("Email")
    var email: String? = null

    @field:SerializedName("Start")
    var starttime: String? = null

    @field:SerializedName("End")
    var endtime: String? = null

    @field:SerializedName("BookDate")
    var bookdate: String? = null

    @field:SerializedName("NumberOfPlayer")
    var numberofplayer: String? = null

    @field:SerializedName("Image")
    var image: String? = null
}