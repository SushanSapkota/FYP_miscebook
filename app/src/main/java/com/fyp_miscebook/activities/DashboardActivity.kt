package com.fyp_miscebook.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fyp_miscebook.R

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_)
        setSupportActionBar(findViewById(R.id.my_toolbar))
    }
}