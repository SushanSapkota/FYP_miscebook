package com.fyp_miscebook.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fyp_miscebook.AppConstants
import com.fyp_miscebook.R
import com.fyp_miscebook.database.userdatabase.UserDataBaseHandler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_NAME = AppConstants.SharedPreference_login
    val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    }
    private var editor: SharedPreferences.Editor? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val user = sharedPreferences.getString(AppConstants.SharedPreference_username,"")?:""
        val context = this
        val Userdb = UserDataBaseHandler(context)
        val data = Userdb.getall(user)

        name.text = ""
        email.text = ""
        username.text = ""
        address.text = ""
        mobile.text = ""

        for (i in 0 until data.size) {
            name.append(data[i].firstname + " " + data[i].middlename + data[i].lastname)
            email.append(data[i].email)
            username.append(data[i].username)
            address.append(data[i].address)
            mobile.append(data[i].mobile)
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