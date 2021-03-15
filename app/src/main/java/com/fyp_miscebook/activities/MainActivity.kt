package com.fyp_miscebook.activities


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.fyp_miscebook.ApiClient
import com.fyp_miscebook.R
import com.fyp_miscebook.RecyclerViewAdapter
import com.fyp_miscebook.UsersAdapter
import com.fyp_miscebook.model.UserResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var listUsers: MutableList<UserResponse> = mutableListOf<UserResponse>()
    private var adapter: UsersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listUsers = mutableListOf()
        getUsersData()

//        recycler_main.layoutManager = LinearLayoutManager(this@MainActivity)
//        adapter = UsersAdapter(
//            this,
//            listUsers
//        )
//        recycler_main.adapter = adapter


    }

    private fun getUsersData() {
        ApiClient.apiService.getUsers().enqueue(object : Callback<MutableList<UserResponse>> {
            override fun onFailure(call: Call<MutableList<UserResponse>>, t: Throwable) {
                Log.e("error", t.localizedMessage)

                animation_view.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MutableList<UserResponse>>,
                response: Response<MutableList<UserResponse>>
            ) {
                val usersResponse = response.body()
                listUsers.clear()
                usersResponse?.let { listUsers.addAll(it) }
//                val recyclerView: RecyclerView = findViewById(R.id.recycler_main)
                adapter = UsersAdapter(this@MainActivity,listUsers)
                val layoutManager = LinearLayoutManager(applicationContext)
                recycler_main.layoutManager = layoutManager
                recycler_main.itemAnimator = DefaultItemAnimator()
                recycler_main.adapter = adapter

//                adapter?.notifyDataSetChanged()
                Toast.makeText(this@MainActivity,"SUCCESS",LENGTH_LONG).show()
            }

        })


    }

}