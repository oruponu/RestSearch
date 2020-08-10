package com.oruponu.restsearch.ui.view

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.registerForActivityResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.oruponu.restsearch.R

class MainActivity : BaseActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestLocationPermission: ActivityResultLauncher<Unit> =
        registerForActivityResult(RequestPermission(), ACCESS_FINE_LOCATION) {
            if (it) {
                getLastLocation()
            } else {
                showLocationPermissionMessage()
            }
        }

    private var latitude = .0
    private var longitude = .0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestLocationPermission.launch(Unit)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            val location = it.result
            if (it.isSuccessful && location != null) {
                latitude = location.latitude
                longitude = location.longitude
            } else {
                showLocationPermissionMessage()
            }
        }
    }

    private fun showLocationPermissionMessage() = showSnackbar(
        findViewById(android.R.id.content),
        R.string.request_location_permission,
        android.R.string.ok,
        View.OnClickListener { requestLocationPermission.launch(Unit) })
}
