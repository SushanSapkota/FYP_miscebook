package com.fyp_miscebook.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fyp_miscebook.AppConstants
import com.fyp_miscebook.R
import com.fyp_miscebook.activities.admin.AdminActivity
import com.fyp_miscebook.api.ApiClient
import com.fyp_miscebook.model.UserResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_dashboard_.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = AppConstants.SharedPreference_login
    var tempDialog: ProgressDialog? = null
    private var listuser: ArrayList<UserResponse> = ArrayList()
    val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    }
    private var editor: Editor? = null

    // For User checking
    var adminUser = false
    var verifiedUser = false
    lateinit var userData: String

    lateinit var saveCheckBox: CheckBox

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editor = sharedPreferences.edit()

        initUI()
        //creating sharedpreference
        val savelogin = sharedPreferences.getBoolean(AppConstants.SharedPreference_savelogin, true)

        //storing email and password in shared preference when checkbox is checked
        if (savelogin) {
            activity_login_username.setText(sharedPreferences.getString("email", null))
        }

        btn_login.setOnClickListener {

            val username = activity_login_username.text.toString()
            val password = activity_login_password.text.toString()

            //checking if email and password is entered or not
            if (username.isEmpty()) {
                activity_login_username.error = "Email is empty"
                activity_login_username.isFocusable = true
            } else if (password.isEmpty()) {
                activity_login_password.error = "Password is empty"
                activity_login_password.isFocusable = true
            } else if (password.length < 8) {
                activity_login_password.error = "Password must have 8 characters"
                activity_login_password.isFocusable = true
            } else {
                data(username, password)
                showProgressDialog()
            }
        }

        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun initUI() {
        saveCheckBox = findViewById(R.id.checkBox)
    }

    private fun data(username: String, password: String) {
        ApiClient.apiService.getUsers().enqueue(object : Callback<ArrayList<UserResponse>> {
            override fun onFailure(call: Call<ArrayList<UserResponse>>, t: Throwable) {

                Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_SHORT)
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

                for (i in 0 until listuser.size) {
                    if (username.equals(
                            listuser[i].username,
                            true
                        ) && password.equals(listuser[i].password, false)
                    ) {
                        adminUser = listuser[i].admin!!
                        verifiedUser = true
                        userData = listuser[i].username!!
                        if (saveCheckBox.isChecked)
                            saveUserData(userData)
                        break

                    } else {
                        verifiedUser = false
                    }
                }

                if (verifiedUser) {
                    success(adminUser)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid Credentials",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                dismissProgressDialog()

            }
        })
    }


    fun success(admin: Boolean) {
        if (admin) {
            Toast.makeText(this, "Admin Login Success", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AdminActivity::class.java))

        } else {
            Toast.makeText(this, "Hello," + userData, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DashboardActivity::class.java))
        }
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
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                dialog.dismiss()
                // Respond to negative button press
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                // Respond to positive button press
                dialog.dismiss()
                val exitIntent = Intent(Intent.ACTION_MAIN)
                exitIntent.addCategory(Intent.CATEGORY_HOME)
                exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(exitIntent)
            }
            .show()
    }

    private fun saveUserData(username: String?) {
        //save username and password when remember me is ticked
        editor!!.clear()
        editor!!.putBoolean(AppConstants.SharedPreference_savelogin, true)
        editor!!.putString(
            AppConstants.SharedPreference_username, username
        )
        editor!!.putBoolean(AppConstants.SharedPreference_logged, true)
        editor!!.commit()
        startActivity(
            Intent(
                this@LoginActivity,
                DashboardActivity::class.java
            )
        )
    }
}