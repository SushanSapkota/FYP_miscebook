package com.fyp_miscebook.activities

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp_miscebook.R
import com.fyp_miscebook.adapter.RecyclerViewAdapter
import com.fyp_miscebook.dataModel.FutsalDataModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.persistent_bottomsheet.*

class MapActivity : AppCompatActivity() {

    var supportMapFragment: SupportMapFragment? = null
    var client: FusedLocationProviderClient? = null

    //    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        supportMapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?

        client = LocationServices.getFusedLocationProviderClient(this)

        permissioncheck()

        bottomsheet()

        data()
    }

    private fun permissioncheck() {
        if (ActivityCompat.checkSelfPermission(
                this@MapActivity,
                permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getCurrentLocation()
        } else {
            ActivityCompat.requestPermissions(
                this@MapActivity,
                arrayOf(permission.ACCESS_FINE_LOCATION),
                44
            )
        }
    }

    private fun bottomsheet() {
//        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//
//        bottomSheetBehavior.addBottomSheetCallback(object :
//            BottomSheetBehavior.BottomSheetCallback() {
//
//            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                // handle onSlide
//            }
//
//            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(
//                        this@MapActivity,
//                        "STATE_COLLAPSED",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(
//                        this@MapActivity,
//                        "STATE_EXPANDED",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(
//                        this@MapActivity,
//                        "STATE_DRAGGING",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(
//                        this@MapActivity,
//                        "STATE_SETTLING",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(
//                        this@MapActivity,
//                        "STATE_HIDDEN",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    else -> Toast.makeText(this@MapActivity, "OTHER_STATE", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            }
//        })
    }

    private fun data() {
        linearLayoutManager = LinearLayoutManager(this)
        futsal.layoutManager = linearLayoutManager

        //Recycler view
        adapter = RecyclerViewAdapter(
            this,
            arrayListOf(
                FutsalDataModel(resources.getDrawable(R.drawable.logo), "Mates Futsal", "Bafal"),
                FutsalDataModel(
                    resources.getDrawable(R.drawable.logo),
                    "Chaitya Futsal",
                    "Solteemode"
                ),
                FutsalDataModel(resources.getDrawable(R.drawable.logo), "United Futsal", "Bafal"),
                FutsalDataModel(
                    resources.getDrawable(R.drawable.logo),
                    "Maidan Futsal",
                    "Battisputali"
                ),
                FutsalDataModel(resources.getDrawable(R.drawable.logo), "Futsal Arena", "Thamel")
            )
        )

        futsal.adapter = adapter
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                permission.ACCESS_FINE_LOCATION
            ) !=
            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val task = client!!.lastLocation
        task.addOnSuccessListener { location: Location? ->
            if (location != null) {
                supportMapFragment!!.getMapAsync { googleMap: GoogleMap ->
                    val latLng = LatLng(
                        location.latitude, location.longitude
                    )
                    val options = MarkerOptions().position(latLng)
                        .title("I am Here")
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                    googleMap.addMarker(options)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == 44) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, DashboardActivity::class.java))
        overridePendingTransition(0, 0)
        super.onBackPressed()
    }

}