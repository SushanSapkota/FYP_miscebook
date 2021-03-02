package com.fyp_miscebook.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fyp_miscebook.AppConstants
import com.fyp_miscebook.R
import com.fyp_miscebook.database.userdatabase.UserDataBaseHandler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = AppConstants.SharedPreference_login
    val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    }
    private var editor: Editor? = null

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editor = sharedPreferences.edit()

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
                signIn(username, password)
            }
        }

        register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    fun signIn(username: String, password: String) {
        if (checkBox.isChecked) {
            //save username and password when remember me is ticked

            //save username and password when remember me is ticked
            editor!!.clear()
            editor!!.putBoolean(AppConstants.SharedPreference_savelogin, true)
            editor!!.putString(AppConstants.SharedPreference_username, username)
            editor!!.putBoolean(AppConstants.SharedPreference_logged, true)
            editor!!.commit()

            if (username == "Admin" && password == "Admin123") {
                //startActivity(Intent(this,AdminActivity::java.class))
                Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show()
            } else {
                val context = this
                val Userdb = UserDataBaseHandler(context)
                Userdb.login(username, password, this)
            }

        } else {
            //save username and password when remember me is ticked
            editor!!.clear()
            editor!!.putBoolean(AppConstants.SharedPreference_savelogin, false)
            editor!!.putString(AppConstants.SharedPreference_username, username)
            editor!!.commit()

            if (username == "Admin" && password == "Admin123") {
                //startActivity(Intent(this,AdminActivity::java.class))
                Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show()
            } else {
                val context = this
                val Userdb = UserDataBaseHandler(context)
                Userdb.login(username, password, this)
            }
        }
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
}