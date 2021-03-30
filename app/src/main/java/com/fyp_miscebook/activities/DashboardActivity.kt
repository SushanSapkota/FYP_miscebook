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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp_miscebook.AppConstants
import com.fyp_miscebook.R
import com.fyp_miscebook.adapter.TopVenueAdapter
import com.fyp_miscebook.api.ApiClient
import com.fyp_miscebook.model.TopVenueResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard_.*
import kotlinx.android.synthetic.main.activity_dashboard_.view.ic_search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private var topvenueadapter: TopVenueAdapter? = null
    var tempDialog: ProgressDialog? = null
    private var listVenue: ArrayList<TopVenueResponse> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        username()

        data()
        showProgressDialog()

        my_toolbar.ic_search.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

    }

    @SuppressLint("SetTextI18n")
    private fun username() {
        sharedPreferences =
            getSharedPreferences(AppConstants.SharedPreference_login, Context.MODE_PRIVATE)

        val username = sharedPreferences!!.getString(AppConstants.SharedPreference_username, "")
        txt_user_name.text = "Hi $username"

    }

    private fun data() {
        ApiClient.apiService.getVenue().enqueue(object : Callback<ArrayList<TopVenueResponse>> {
            override fun onFailure(call: Call<ArrayList<TopVenueResponse>>, t: Throwable) {

                animation_view.visibility = View.VISIBLE
                Toast.makeText(this@DashboardActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
                dismissProgressDialog()
            }

            override fun onResponse(
                call: Call<ArrayList<TopVenueResponse>>,
                response: Response<ArrayList<TopVenueResponse>>
            ) {
                val topvenueresponse = response.body()
                listVenue.clear()

                listVenue = topvenueresponse as ArrayList<TopVenueResponse>
                topvenueadapter = TopVenueAdapter(this@DashboardActivity, listVenue)

                Log.d("API", Gson().toJson(listVenue))
                val layoutManager = LinearLayoutManager(applicationContext)
                RecyclerView.layoutManager = layoutManager
                RecyclerView.itemAnimator = DefaultItemAnimator()
                RecyclerView.adapter = topvenueadapter

                Toast.makeText(this@DashboardActivity, "SUCCESS", Toast.LENGTH_LONG).show()
                dismissProgressDialog()
            }
        })
    }

    override fun onStart() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnavigation)

        bottomNavigationView.selectedItemId = R.id.action_home

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> return@setOnNavigationItemSelectedListener true
                R.id.action_booking -> {
                    startActivity(Intent(this, BookingActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
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

    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.app_name))
            .setMessage(resources.getString(R.string.exit_text))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, _ ->
                dialog.dismiss()
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ ->
                // Respond to positive button press
                dialog.dismiss()
                val exitIntent = Intent(Intent.ACTION_MAIN)
                exitIntent.addCategory(Intent.CATEGORY_HOME)
                exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(exitIntent)
            }
            .show()
    }
}