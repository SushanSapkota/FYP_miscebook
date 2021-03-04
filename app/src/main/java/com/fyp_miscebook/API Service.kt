package com.fyp_miscebook

import android.util.Log
import com.fyp_miscebook.model.Futsal
import com.fyp_miscebook.model.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface APIService {
    @GET("rest/user")
        suspend fun getUsers(): Response<ArrayList<UserResponse>>

        @GET("/rest/futsal")
        suspend fun getFutsalLists(): Response<ArrayList<Futsal>>

        // ...
}

fun rawJSON() {

    // Create Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("https://miscebook-92e7.restdb.io/")
        .build()

    // Create Service
    val service = retrofit.create(APIService::class.java)

    // Create JSON using JSONObject
    val jsonObject = JSONObject()
    jsonObject.put("name", "Jack")
    jsonObject.put("salary", "3540")
    jsonObject.put("age", "23")

    // Convert JSONObject to String
    val jsonObjectString = jsonObject.toString()

    // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
    val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

    CoroutineScope(Dispatchers.IO).launch {
        // Do the POST request and get response
        val response = service.getUsers()

        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {


                Log.d("UserDetail :", response.body()!!.get(0).firstName!!)

            } else {

                Log.e("RETROFIT_ERROR", response.code().toString())

            }
        }
    }
}