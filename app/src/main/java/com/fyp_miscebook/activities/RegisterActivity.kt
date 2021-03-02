package com.fyp_miscebook.activities

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fyp_miscebook.R
import com.fyp_miscebook.database.userdatabase.UserDataBaseHandler
import com.fyp_miscebook.database.userdatabase.UserEntity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

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
            val user = UserEntity()
            user.firstname = Firstname
            user.middlename = Middlename
            user.lastname = Lastname
            user.email = Email
            user.username = Username
            user.password = Password
            user.address = Address
            user.mobile = Mobile
            val context = this
            val Userdb = UserDataBaseHandler(context)
            Userdb.insertData(user, this)
        } else {
            Toast.makeText(this, "Terms and condition not accepted", Toast.LENGTH_SHORT).show()
        }
    }
}