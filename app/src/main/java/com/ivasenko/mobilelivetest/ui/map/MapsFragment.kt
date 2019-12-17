package com.ivasenko.mobilelivetest.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ivasenko.mobilelivetest.R
import com.ivasenko.mobilelivetest.ui.dialogs.RadiusDialog
import com.ivasenko.mobilelivetest.ui.shared.SharedViewModel
import com.ivasenko.mobilelivetest.utils.askPermission
import com.ivasenko.mobilelivetest.utils.hasPermission
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class MapsFragment : Fragment(), KodeinAware, OnMapReadyCallback {

    override val kodein by closestKodein()
    private val viewModelFactory: MapsViewModelFactory by instance()

    private lateinit var viewModel: MapsViewModel
    private lateinit var sharedViewModel: SharedViewModel

    private val locationPermissionRequestCode = 5000
    private val defaultLatLng = LatLng(-34.0, 151.0)
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.maps_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MapsViewModel::class.java)
        sharedViewModel = ViewModelProviders.of(requireActivity())
            .get(SharedViewModel::class.java)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMinZoomPreference(9f)

        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
        if (hasPermission(*permissions))
            setCurrentLocation()
        else
            askPermission(permissions = *permissions, requestCode = locationPermissionRequestCode)

        viewModel.tweetsList.observe(viewLifecycleOwner, Observer { list ->
            mMap.clear()
            list.forEach { mapMarker ->
                val marker =
                    mMap.addMarker(MarkerOptions().position(mapMarker.position).title(mapMarker.getTitle()))
                mapMarker.marker = marker
            }
        })

        val tweetView = TweetView(layoutInflater)
        mMap.setInfoWindowAdapter(tweetView)
        mMap.setOnInfoWindowClickListener { marker ->
            val tweetMapMarker = viewModel.getTweetMapMarker(marker)
            tweetMapMarker?.let { sharedViewModel.currentMarker(it) }
            findNavController().navigate(R.id.tweetDetailsFragment)
            marker.hideInfoWindow()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode == locationPermissionRequestCode
                && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            true -> setCurrentLocation()
            false -> Toast.makeText(
                context,
                getString(R.string.denied_permission_toast_text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.radius -> {
                val dialog = RadiusDialog.newInstance(viewModel.radius)
                dialog.show(childFragmentManager, RadiusDialog.TAG)
            }
            R.id.search -> findNavController().navigate(R.id.searchTweetsFragment)
        }
        return true
    }

    private fun setCurrentLocation() {
        val client = LocationServices.getFusedLocationProviderClient(requireActivity())
        client.lastLocation.addOnSuccessListener { location ->
            val currentPosition = LatLng(location.latitude, location.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition))
            viewModel.loadTweets(currentPosition)
        }
        client.lastLocation.addOnFailureListener {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLatLng))
        }
    }
}