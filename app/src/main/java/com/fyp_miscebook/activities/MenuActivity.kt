package com.fyp_miscebook.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fyp_miscebook.AppConstants
import com.fyp_miscebook.R
import com.fyp_miscebook.adapter.UsersAdapter
import com.fyp_miscebook.api.ApiClient
import com.fyp_miscebook.model.UserResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    var tempDialog: ProgressDialog? = null
    private var listUsers: MutableList<UserResponse> = mutableListOf<UserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        sharedPreferences =
            getSharedPreferences(AppConstants.SharedPreference_login, Context.MODE_PRIVATE)
        val user = sharedPreferences!!.getString(AppConstants.SharedPreference_username, "")

        showProgressDialog()

        listUsers = mutableListOf()

        username(user!!)
    }

    @SuppressLint("SetTextI18n")
    private fun username(user: String) {
        txt_user_name.text = "Hi $user"
        dismissProgressDialog()

        ApiClient.apiService.getUsers().enqueue(object : Callback<MutableList<UserResponse>> {
            override fun onFailure(call: Call<MutableList<UserResponse>>, t: Throwable) {

                Toast.makeText(this@MenuActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
                dismissProgressDialog()
            }

            override fun onResponse(
                call: Call<MutableList<UserResponse>>,
                response: Response<MutableList<UserResponse>>
            ) {
                val usersResponse = response.body()
                listUsers.clear()
                usersResponse?.let { listUsers.addAll(it) }
                Toast.makeText(this@MenuActivity, "SUCCESS", Toast.LENGTH_LONG).show()
                dismissProgressDialog()
            }

        })

//        for (i in 1..listUsers.size) {
//            if (listUsers.get(i).userName == user) {
//                Username_TXT.text = listUsers[i].userName
//                Email_TXT.text = listUsers[i].email
//                profile_picture.let {
//                    Glide.with(this@MenuActivity).load(listUsers[i].image).into(it)
//                }
//            }
//        }
    }

    override fun onStart() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnavigation)

        bottomNavigationView.selectedItemId = R.id.action_menu

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    startActivity(Intent(this, DashboardActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_booking -> {
                    startActivity(Intent(this, BookingActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_menu -> return@setOnNavigationItemSelectedListener true
            }
            false
        }
        super.onStart()
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

    fun showProgressDialog() {
        tempDialog = ProgressDialog(this)
        tempDialog!!.setCancelable(true)
        tempDialog!!.setMessage("Loading Data...")
        tempDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        tempDialog!!.show()
    }

    fun dismissProgressDialog() {
        tempDialog!!.dismiss()
    }
}