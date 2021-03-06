package com.oruponu.restsearch.ui.view

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.oruponu.restsearch.R
import com.oruponu.restsearch.databinding.CategoryMainBinding
import com.oruponu.restsearch.extensions.countTotalPage
import com.oruponu.restsearch.extensions.toBounds
import com.oruponu.restsearch.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), OnMapReadyCallback {
    private val viewModel: MainViewModel by viewModels()

    private val progressFragment = ProgressFragment()

    private val requestLocationPermission: ActivityResultLauncher<Unit> =
        registerForActivityResult(RequestPermission(), ACCESS_FINE_LOCATION) {
            if (it) {
                startLocationUpdates()
            } else {
                showLocationPermissionMessage()
            }
        }

    var isMyLocationEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestLocationPermission.launch(Unit)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        registerObserver()
        setOnClickListener()
        setSearchCategory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (viewModel.latitude == 0.0 && viewModel.longitude == 0.0) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(36.0, 138.0), 3.6f))
        }
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style_json))
        googleMap.uiSettings.isZoomControlsEnabled = true
        viewModel.googleMap = googleMap
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest =
            LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
        val locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                val location = locationResult.lastLocation
                viewModel.latitude = location.latitude
                viewModel.longitude = location.longitude
                setMapCircle(spinner.selectedItemPosition, !isMyLocationEnabled)
                if (!isMyLocationEnabled) {
                    viewModel.googleMap.isMyLocationEnabled = true
                    isMyLocationEnabled = true
                }
            }
        }
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun showLocationPermissionMessage() = showSnackbar(
        findViewById(android.R.id.content),
        R.string.request_location_permission,
        android.R.string.ok,
        View.OnClickListener { requestLocationPermission.launch(Unit) })

    private fun registerObserver() {
        viewModel.dataRestSearch.observe(this, Observer {
            progressFragment.dismiss()
            val intent = ResultActivity.intent(
                this,
                it.countTotalPage(),
                viewModel.getCategoriesCode(),
                viewModel.latitude,
                viewModel.longitude,
                (spinner.selectedItemId + 1).toInt()
            )
            startActivity(intent)
        })

        viewModel.stringId.observe(this, Observer {
            progressFragment.dismiss()
            it.getContentIfNotHandled()?.let { stringId ->
                showSnackbar(
                    findViewById(android.R.id.content),
                    stringId,
                    android.R.string.ok,
                    View.OnClickListener { return@OnClickListener })
            }
        })
    }

    private fun setOnClickListener() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) = setMapCircle(position, true)

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        filterButton.setOnClickListener {
            val launcher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    if (it.resultCode == Activity.RESULT_OK) {
                        val serializable = it.data?.getSerializableExtra("selected_categories")
                        @Suppress("UNCHECKED_CAST")
                        viewModel.selectedCategories = serializable as HashMap<String, String>
                        setSearchCategory()
                    }
                }

            val intent = SearchOptionsActivity.intent(this, viewModel.selectedCategories)
            launcher.launch(intent)
        }

        searchButton.setOnClickListener {
            progressFragment.show(supportFragmentManager, "progress")
            viewModel.search((spinner.selectedItemId + 1).toInt())
        }
    }

    private fun setMapCircle(spinnerSelectedItemId: Int, isControlCameraUpdate: Boolean = false) {
        if (viewModel.latitude == 0.0 && viewModel.longitude == 0.0) {
            return
        }
        val latLng = LatLng(viewModel.latitude, viewModel.longitude)
        val rangeString = spinner.getItemAtPosition(spinnerSelectedItemId).toString()
        var range = 0.0
        if (rangeString.endsWith("km")) {
            range = rangeString.replace("km", "").toDouble() * 1000
        } else if (rangeString.endsWith("m")) {
            range = rangeString.replace("m", "").toDouble()
        }

        viewModel.googleMap.clear()
        viewModel.googleMap.addCircle(
            CircleOptions().center(latLng).radius(range)
                .strokeColor(ContextCompat.getColor(spinner.context, R.color.mapCircleColor))
                .fillColor(ContextCompat.getColor(spinner.context, R.color.mapCircleColor))
        )

        if (isControlCameraUpdate) {
            viewModel.googleMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(latLng.toBounds(range), 32)
            )
        }
    }

    private fun setSearchCategory() {
        flexboxLayout.removeAllViews()
        notSelectedTextView.visibility = View.GONE
        viewModel.selectedCategories.forEach { category ->
            val categoryItem = DataBindingUtil.inflate<CategoryMainBinding>(
                LayoutInflater.from(flexboxLayout.context),
                R.layout.category_main,
                flexboxLayout,
                true
            )
            categoryItem.chip.text = category.value
            categoryItem.chip.setOnCloseIconClickListener {
                viewModel.selectedCategories.remove(category.key)
                categoryItem.chip.visibility = View.GONE
                if (viewModel.selectedCategories.isEmpty()) {
                    notSelectedTextView.visibility = View.VISIBLE
                }
            }
        }
    }
}
