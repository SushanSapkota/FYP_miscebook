package com.fyp_miscebook.api

import com.fyp_miscebook.database.BookingEntity
import com.fyp_miscebook.database.UserEntity
import com.fyp_miscebook.model.FutsalResponse
import com.fyp_miscebook.model.TopVenueResponse
import com.fyp_miscebook.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("userslist")
    @Headers("x-apikey: ffbb1817873440bf72d76280e70790d377f22")
    fun getUsers(): Call<ArrayList<UserResponse>>

    @POST("userslist")
    fun addUser(@HeaderMap headers: Map<String, String>, @Body userData: UserEntity): Call<UserEntity>

    @POST("booking")
    fun booking(@HeaderMap headers: Map<String, String>, @Body bookingdetail: BookingEntity): Call<BookingEntity>

    @GET("futsal")
    @Headers("x-apikey: ffbb1817873440bf72d76280e70790d377f22")
    fun getFutsal(): Call<ArrayList<FutsalResponse>>

//    @GET("cricsal")
//    fun getCricsal(): Call<MutableList<CricsalResponse>>

//    @GET("archery")
//    fun getArchery(): Call<MutableList<ArcherylResponse>>

    @GET("topvenue")
    @Headers("x-apikey: ffbb1817873440bf72d76280e70790d377f22")
    fun getVenue(): Call<ArrayList<TopVenueResponse>>
}