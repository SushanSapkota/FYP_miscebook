package com.fyp_miscebook.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fyp_miscebook.AppConstants
import com.fyp_miscebook.R

class SplashActivity : AppCompatActivity() {

    var active = true
    var splashTime = 2000 // time to display the splash screen in ms

    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val context = this
        sharedPreferences =
            getSharedPreferences(AppConstants.SharedPreference_login, Context.MODE_PRIVATE)
        val logged = sharedPreferences!!.getBoolean(AppConstants.SharedPreference_logged, false)
        val splashTread: Thread = object : Thread() {
            override fun run() {
                try {
                    var waited = 0
                    while (active && waited < splashTime) {
                        sleep(100)
                        if (active) {
                            waited += 100
                        }
                    }
                } catch (e: Exception) {
                } finally {
                    if (logged) {
                        startActivity(
                            Intent(
                                this@SplashActivity,
//                                MainActivity::class.java
                                DashboardActivity::class.java
                            )
                        )
                    } else {
                        startActivity(
                            Intent(
                                this@SplashActivity,
                                LoginActivity::class.java
                            )
                        )
                    }
                }
            }
        }
        splashTread.start()
    }
}