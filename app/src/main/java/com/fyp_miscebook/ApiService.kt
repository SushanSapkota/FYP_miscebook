package com.fyp_miscebook

import com.fyp_miscebook.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("users")
    fun getUsers(): Call<MutableList<UserResponse>>

}