package com.oruponu.restsearch.ui.view

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.oruponu.restsearch.R
import com.oruponu.restsearch.data.model.rest.Rest
import com.oruponu.restsearch.databinding.ActivityMainBinding
import com.oruponu.restsearch.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val progressFragment = ProgressFragment()

    private val requestLocationPermission: ActivityResultLauncher<Unit> =
        registerForActivityResult(RequestPermission(), ACCESS_FINE_LOCATION) {
            if (it) {
                getLastLocation()
            } else {
                showLocationPermissionMessage()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.also {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        registerObserver()
        setOnClickListener()

        requestLocationPermission.launch(Unit)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener {
            val location = it.result
            if (it.isSuccessful && location != null) {
                viewModel.latitude = location.latitude
                viewModel.longitude = location.longitude
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

    private fun registerObserver() {
        viewModel.dataRest.observe(this, Observer {
            progressFragment.dismiss()
            val intent = ResultActivity.intent(this, it.rest as ArrayList<Rest>)
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
        filterButton.setOnClickListener {
            val launcher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    if (it.resultCode == Activity.RESULT_OK) {
                        val serializable = it.data?.getSerializableExtra("selected_categories")
                        @Suppress("UNCHECKED_CAST")
                        viewModel.selectedCategories.postValue(serializable as HashMap<String, String>)
                    }
                }

            val intent = SearchOptionsActivity.intent(
                this,
                viewModel.selectedCategories.value ?: hashMapOf()
            )
            launcher.launch(intent)
        }

        searchButton.setOnClickListener {
            progressFragment.show(supportFragmentManager, "progress")
            viewModel.search(spinner.selectedItemId + 1)
        }
    }
}
