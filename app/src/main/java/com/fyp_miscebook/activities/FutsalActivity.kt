package com.fyp_miscebook.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.fyp_miscebook.AppConstants
import com.fyp_miscebook.R
import com.fyp_miscebook.database.FutsalDataBaseHandler
import com.fyp_miscebook.database.FutsalEntity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_futsal.*

class FutsalActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var PRIVATE_MODE = 0
    private val PREF_NAME = AppConstants.SharedPreference_login
    val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    }

    var name: String? = null
    var address: String? = null
    var email: String? = null
    var image: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_futsal)
        name = intent.getStringExtra("name").toString()
        address = intent.getStringExtra("address").toString()
        email = intent.getStringExtra("email").toString()
        image = intent.getStringExtra("image").toString()

        activity_futsal_name.setText(name)
        activity_futsal_address.setText(address)
        activity_futsal_email.setText(email)
        Glide.with(this).load(image).into(futsal_image)

        toolbar.setNavigationOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        btn_book_futsal.setOnClickListener {
            bookfutsal()
        }
    }

    private fun bookfutsal() {
        val futsal = FutsalEntity()
        futsal.name = name.toString()
        futsal.address = address.toString()
        futsal.email = email.toString()
        futsal.start = activity_futsal_start.text.toString()
        futsal.end = activity_futsal_end.text.toString()
        futsal.bookwhen = activity_futsal_date.text.toString()
        futsal.no_ofplayers = activity_futsal_numberofplayer.text.toString()
        futsal.image = image.toString()
        val context = this
        val Futsaldb = FutsalDataBaseHandler(context)
        Futsaldb.insertData(futsal, this)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, DashboardActivity::class.java))
    }
}