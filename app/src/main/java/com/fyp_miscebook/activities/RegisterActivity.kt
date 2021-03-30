package com.fyp_miscebook.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fyp_miscebook.R
import com.fyp_miscebook.database.UserEntity
import com.fyp_miscebook.api.ApiClient
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    var tempDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            signup(
                et_firstname.text.toString().trim(),
                et_middlename.text.toString().trim(),
                et_lastname.text.toString().trim(),
                et_email.text.toString().trim(),
                et_username.text.toString().trim(),
                et_password.text.toString().trim(),
                et_confirm_password.text.toString().trim(),
                et_address.text.toString().trim(),
                et_mobile.text.toString().trim()
            )
        }

        backHome.setOnClickListener {
            onBackPressed()
        }
    }

    private fun signup(
        Firstname: String,
        Middlename: String,
        Lastname: String,
        Email: String,
        Username: String,
        Password: String,
        ConfirmPassword: String,
        Address: String,
        Mobile: String
    ) {
        val userEntity = UserEntity()

        if (Firstname.isEmpty()) {
            et_firstname.error = "First name is empty"
            et_firstname.isFocusable = true
            return
        }
        if (Lastname.isEmpty()) {
            et_lastname.error = "Last name is empty"
            et_lastname.isFocusable = true
            return
        }
        if (Email.isEmpty()) {
            et_email.error = "Email is empty"
            et_email.isFocusable = true
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            et_email.error = "Invalid Email"
            et_email.isFocusable = true
        }
        if (Username.isEmpty()) {
            et_username.error = "Username is empty"
            et_username.isFocusable = true
            return
        }
        if (Password.isEmpty()) {
            et_password.error = "Password is empty"
            et_password.isFocusable = true
            return
        }
        if (Password.length < 8) {
            activity_login_password.error = "Password must have 8 characters"
            activity_login_password.isFocusable = true
        }
        if (ConfirmPassword.isEmpty()) {
            et_confirm_password.error = "Confirm Password is empty"
            et_confirm_password.isFocusable = true
            return
        }
        if (Password != ConfirmPassword) {
            et_password.error = "Password and Confirm Password must be same"
            et_password.isFocusable = true
            et_confirm_password.error = "Password and Confirm Password must be same"
            et_confirm_password.isFocusable = true
            return
        }
        if (Address.isEmpty()) {
            et_address.error = "Address is empty"
            et_address.isFocusable = true
            return
        }
        if (Mobile.isEmpty()) {
            et_mobile.error = "Mobile number is empty"
            et_mobile.isFocusable = true
            return
        }
        if (Mobile.length < 10) {
            et_mobile.error = "Mobile number must have 8 characters"
            et_mobile.isFocusable = true
            return
        }
        if (chkBox1.isChecked) {
            userEntity.firstname = Firstname
            userEntity.middlename = Middlename
            userEntity.lastname = Lastname
            userEntity.email = Email
            userEntity.username = Username
            userEntity.password = Password
            userEntity.address = Address
            userEntity.mobile = Mobile
            addUser(userEntity)
            showProgressDialog()
        } else {
            Toast.makeText(this, "Terms and condition not accepted", Toast.LENGTH_SHORT).show()
        }
    }

    fun addUser(userData: UserEntity) {
        val header = HashMap<String, String>()
        header["x-apikey"] = "ffbb1817873440bf72d76280e70790d377f22"
        header["Content-Type"] = "application/json"

        ApiClient.apiService.addUser(header, userData).enqueue(object : Callback<UserEntity> {

            override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
                dismissProgressDialog()
            }

            override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                if (response.code() == 200) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                    dismissProgressDialog()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    dismissProgressDialog()
                }
            }
        })
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
