package com.fyp_miscebook.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fyp_miscebook.AppConstants
import com.fyp_miscebook.R
import com.fyp_miscebook.api.ApiClient
import com.fyp_miscebook.model.UserResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    var tempDialog: ProgressDialog? = null
    private var listuser: ArrayList<UserResponse> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        sharedPreferences =
            getSharedPreferences(AppConstants.SharedPreference_login, Context.MODE_PRIVATE)
        val user = sharedPreferences!!.getString(AppConstants.SharedPreference_username, "")

        showProgressDialog()

        username(user!!)

        data(user)

        logoutTab.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun username(user: String) {
        txt_user_name.text = "Hi $user"
    }

    private fun data(username: String) {
        ApiClient.apiService.getUsers().enqueue(object : Callback<ArrayList<UserResponse>> {
            override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {

                Toast.makeText(this@MenuActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
                dismissProgressDialog()
            }

            override fun onResponse(
                call: Call<ArrayList<UserResponse>>,
                response: Response<ArrayList<UserResponse>>
            ) {
                val UserResponse = response.body()
                listuser.clear()

                listuser = UserResponse as ArrayList<UserResponse>

                for (i in 0..listuser.size - 1) {
                    if (username == listuser[i].username) {
                        Username_TXT.text = listuser[i].username
                        Email_TXT.text = listuser[i].email
                        dismissProgressDialog()
                    }
                }
            }
        })
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