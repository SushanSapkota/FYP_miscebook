package com.fyp_miscebook.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp_miscebook.AppConstants
import com.fyp_miscebook.R
import com.fyp_miscebook.adapter.FutsalAdapter
import com.fyp_miscebook.adapter.TopVenueAdapter
import com.fyp_miscebook.api.ApiClient
import com.fyp_miscebook.model.FutsalResponse
import com.fyp_miscebook.model.TopVenueResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_booking.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private var type: String? = null
    private var listVenue: ArrayList<TopVenueResponse> = ArrayList()
    private var listFutsal: ArrayList<FutsalResponse> = ArrayList()
    private var futsaladapter: FutsalAdapter? = null
    private var topvenueadapter: TopVenueAdapter? = null
    var tempDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        username()

        search.setOnClickListener {
            type = category.text.toString()
            setRecyclerdata(type!!)
            showProgressDialog()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun username() {
        sharedPreferences =
            getSharedPreferences(AppConstants.SharedPreference_login, Context.MODE_PRIVATE)

        val username = sharedPreferences!!.getString(AppConstants.SharedPreference_username, "")
        txt_user_name.text = "Hi $username"

    }

    private fun setRecyclerdata(category: String) {
        if (category == "topvenue") {
            animation_view.isVisible = false
            ApiClient.apiService.getVenue()
                .enqueue(object : Callback<ArrayList<TopVenueResponse>> {
                    override fun onFailure(
                        call: Call<ArrayList<TopVenueResponse>>,
                        t: Throwable
                    ) {

                        Log.e("error", t.localizedMessage)

                        animation_view.visibility = View.VISIBLE
                        Toast.makeText(this@BookingActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                            .show()
                        dismissProgressDialog()
                    }

                    override fun onResponse(
                        call: Call<ArrayList<TopVenueResponse>>,
                        response: Response<ArrayList<TopVenueResponse>>
                    ) {
                        val topvenueresponse = response.body()
                        listVenue.clear()
                        topvenueresponse?.let { listVenue.addAll(it) }
                        topvenueadapter = TopVenueAdapter(this@BookingActivity, listVenue)

                        Log.d("API", Gson().toJson(listVenue))
                        val layoutManager = LinearLayoutManager(applicationContext)
                        RecyclerView.layoutManager = layoutManager
                        RecyclerView.itemAnimator = DefaultItemAnimator()
                        RecyclerView.adapter = topvenueadapter

                        Toast.makeText(this@BookingActivity, "SUCCESS", Toast.LENGTH_LONG).show()
                        dismissProgressDialog()
                    }
                })
        } else if (category == "futsal") {
            animation_view.isVisible = false
            ApiClient.apiService.getFutsal()
                .enqueue(object : Callback<ArrayList<FutsalResponse>> {
                    override fun onFailure(call: Call<ArrayList<FutsalResponse>>, t: Throwable) {

                        Log.e("error", t.localizedMessage)

                        animation_view.visibility = View.VISIBLE
                        Toast.makeText(this@BookingActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                            .show()
                        dismissProgressDialog()
                    }

                    override fun onResponse(
                        call: Call<ArrayList<FutsalResponse>>,
                        response: Response<ArrayList<FutsalResponse>>
                    ) {
                        val futsalResponse = response.body()
                        listFutsal.clear()
                        futsalResponse?.let { listFutsal.addAll(it) }
//                val recyclerView: RecyclerView = findViewById(R.id.recycler_main)
                        futsaladapter = FutsalAdapter(this@BookingActivity, listFutsal)

                        Log.d("API", Gson().toJson(listFutsal))
                        val layoutManager = LinearLayoutManager(applicationContext)
                        RecyclerView.layoutManager = layoutManager
                        RecyclerView.itemAnimator = DefaultItemAnimator()
                        RecyclerView.adapter = futsaladapter

//                adapter?.notifyDataSetChanged()
                        Toast.makeText(this@BookingActivity, "SUCCESS", Toast.LENGTH_LONG).show()
                        dismissProgressDialog()
                    }

                })
        } else {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            dismissProgressDialog()
        }
//        else if (category == "cricsal") {
//            animation_view.isVisible = false
//            ApiClient.apiService.getCricsal()
//                .enqueue(object : Callback<MutableList<FutsalResponse>> {
//                    override fun onFailure(call: Call<MutableList<FutsalResponse>>, t: Throwable) {
//
//                        Log.e("error", t.localizedMessage)
//
//                        animation_view.visibility = View.VISIBLE
//                        Toast.makeText(this@BookingActivity, t.localizedMessage, Toast.LENGTH_SHORT)
//                            .show()
//                        dismissProgressDialog()
//                    }
//
//                    override fun onResponse(
//                        call: Call<MutableList<FutsalResponse>>,
//                        response: Response<MutableList<FutsalResponse>>
//                    ) {
//                        val futsalResponse = response.body()
//                        listFutsal.clear()
//                        futsalResponse?.let { listFutsal.addAll(it) }
////                val recyclerView: RecyclerView = findViewById(R.id.recycler_main)
//                        futsaladapter = FutsalAdapter(this@BookingActivity, listFutsal)
//
//                        Log.d("API", Gson().toJson(listFutsal))
//                        val layoutManager = LinearLayoutManager(applicationContext)
//                        RecyclerView.layoutManager = layoutManager
//                        RecyclerView.itemAnimator = DefaultItemAnimator()
//                        RecyclerView.adapter = futsaladapter
//
////                adapter?.notifyDataSetChanged()
//                        Toast.makeText(this@BookingActivity, "SUCCESS", Toast.LENGTH_LONG).show()
//                        dismissProgressDialog()
//                    }
//
//                })
//        }
    }

    override fun onStart() {

        category.setText("")

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnavigation)

        bottomNavigationView.selectedItemId = R.id.action_booking

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_booking ->
                    return@setOnNavigationItemSelectedListener true
                R.id.action_menu -> {
                    startActivity(Intent(this, MenuActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        super.onStart()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, DashboardActivity::class.java))
        overridePendingTransition(0, 0)
        super.onBackPressed()
    }

    fun showProgressDialog() {
        tempDialog = ProgressDialog(this)
        tempDialog!!.setCancelable(false)
        tempDialog!!.setMessage("Loading Data...")
        tempDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        tempDialog!!.show()
    }

    fun dismissProgressDialog() {
        tempDialog!!.dismiss()
    }
}