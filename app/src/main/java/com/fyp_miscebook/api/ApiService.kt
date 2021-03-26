package com.fyp_miscebook.api

import com.fyp_miscebook.UserEntity
import com.fyp_miscebook.model.FutsalResponse
import com.fyp_miscebook.model.TopVenueResponse
import com.fyp_miscebook.model.UserResponse
import com.google.android.material.resources.CancelableFontCallback
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("users")
    fun getUsers(): Call<MutableList<UserResponse>>

    @GET("futsal")
    fun getFutsal(): Call<MutableList<FutsalResponse>>

    @GET("topvenue")
    fun getVenue(): Call<MutableList<TopVenueResponse>>
}