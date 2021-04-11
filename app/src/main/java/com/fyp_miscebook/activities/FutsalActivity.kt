package com.fyp_miscebook.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fyp_miscebook.R
import com.fyp_miscebook.api.ApiClient
import com.fyp_miscebook.database.BookingEntity
import com.fyp_miscebook.model.BookingResponse
import kotlinx.android.synthetic.main.activity_dashboard_.*
import kotlinx.android.synthetic.main.activity_futsal.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FutsalActivity : AppCompatActivity() {

    var id: String? = null
    var name: String? = null
    var address: String? = null
    var email: String? = null
    var image: String? = null
    var currentStarttime: String? = null
    var currentEndtime: String? = null
    var currentBookdate: String? = null
    var currentPlayer: String? = null
    var tempDialog: ProgressDialog? = null

    var timeFormat = SimpleDateFormat("HHmm", Locale.US)
    var dateFormat = SimpleDateFormat("MMddyy", Locale.US)
    private var listBooking: ArrayList<BookingResponse> = ArrayList()


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_futsal)
        id = intent.getStringExtra("id").toString()
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

        btn_starttime.setOnClickListener {
            val start = Calendar.getInstance()
            val startTimePicker = TimePickerDialog(
                this, { view, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)

                    activity_futsal_start.text = timeFormat.format(selectedTime.time)
                },
                start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE), true
            )
            startTimePicker.show()
        }

        btn_endtime.setOnClickListener {
            val end = Calendar.getInstance()
            val endTimePicker = TimePickerDialog(
                this, { view, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    activity_futsal_end.text = timeFormat.format(selectedTime.time)
                },
                end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE), true
            )
            endTimePicker.show()
        }

        btn_date.setOnClickListener {
            val date = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                this, { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    activity_futsal_date.text = dateFormat.format(selectedDate.time)
                },
                date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        btn_book_futsal.setOnClickListener {
            ValidateBooking()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun bookfutsal() {
        val bookingEntity = BookingEntity()

        bookingEntity.futsal_id = id.toString()
        bookingEntity.name = name.toString()
        bookingEntity.address = address.toString()
        bookingEntity.email = email.toString()
        bookingEntity.starttime = currentStarttime.toString()
        bookingEntity.endtime = currentEndtime.toString()
        bookingEntity.bookdate = currentBookdate.toString()
        bookingEntity.numberofplayer = currentPlayer.toString()
        bookingEntity.image = image.toString()
        saveToDatabase(bookingEntity)
        showProgressDialog()
    }

    private fun saveToDatabase(bookingEntity: BookingEntity) {
        val header = HashMap<String, String>()
        header["x-apikey"] = "ffbb1817873440bf72d76280e70790d377f22"
        header["Content-Type"] = "application/json"

        ApiClient.apiService.booking(header, bookingEntity)
            .enqueue(object : Callback<BookingEntity> {

                override fun onFailure(call: Call<BookingEntity>, t: Throwable) {
                    Toast.makeText(this@FutsalActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                    dismissProgressDialog()
                }

                override fun onResponse(
                    call: Call<BookingEntity>,
                    response: Response<BookingEntity>
                ) {
                    if (response.code() == 201) {
                        Toast.makeText(
                            this@FutsalActivity,
                            "Booking Success",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        dismissProgressDialog()
                    } else {
                        Toast.makeText(
                            this@FutsalActivity,
                            "Booking Failed",
                            Toast.LENGTH_SHORT
                        ).show()
                        dismissProgressDialog()
                    }
                }
            })
    }

    fun ValidateBooking() {

        currentStarttime = activity_futsal_start.text.toString()
        currentEndtime = activity_futsal_end.text.toString()
        currentBookdate = activity_futsal_date.text.toString()
        currentPlayer = activity_futsal_numberofplayer.text.toString()

        showProgressDialog()

        ApiClient.apiService.getBooking().enqueue(object : Callback<ArrayList<BookingResponse>> {
            override fun onFailure(call: Call<ArrayList<BookingResponse>>, t: Throwable) {

                Toast.makeText(this@FutsalActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
                dismissProgressDialog()
            }

            override fun onResponse(
                call: Call<ArrayList<BookingResponse>>,
                response: Response<ArrayList<BookingResponse>>
            ) {
                val bookingresponse = response.body()
                listBooking.clear()

                listBooking = bookingresponse as ArrayList<BookingResponse>

                for (i in 0 until listBooking.size) {
                    if (currentBookdate.equals(listBooking[i].bookdate)) {
                        if (currentStarttime!! == listBooking[i].starttime) {
                            Toast.makeText(
                                this@FutsalActivity,
                                "Booking data and starttime is same",
                                Toast.LENGTH_LONG
                            ).show()
                            dismissProgressDialog()
                            break
                        } else {
                            if (currentStarttime!!.toInt() > listBooking[i].starttime!!.toInt() && currentStarttime!!.toInt() < listBooking[i].endtime!!.toInt()) {
                                Toast.makeText(
                                    this@FutsalActivity,
                                    "Booking data and starttime is different but start lies between start and end",
                                    Toast.LENGTH_LONG
                                ).show()
                                dismissProgressDialog()
                                break
                            } else {
                                if (currentEndtime!!.toInt() > listBooking[i].starttime!!.toInt()) {
                                    endtimeValidation()
                                } else {
                                    bookfutsal()
                                }
                            }
                        }
                    } else {
                        bookfutsal()
                        break
                    }
                }
            }
        })
    }

    private fun endtimeValidation() {
        Toast.makeText(
            this,
            "Booking data and starttime is different but end lies between start and end",
            Toast.LENGTH_SHORT
        ).show()
        finish()
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
        startActivity(Intent(this, DashboardActivity::class.java))
    }
}