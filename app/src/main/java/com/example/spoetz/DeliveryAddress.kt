package com.example.spoetz

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.Locale

class DeliveryAddress : AppCompatActivity() {


    private lateinit var latitudeTextView : TextView
    private lateinit var longitudeTextView : TextView
    private lateinit var providerTextView : TextView
    private lateinit var countryTextView : TextView
    private lateinit var addressTextView : TextView


    private lateinit var getLocationButton: Button

    private val LOCATION_PERMISSION_REQ_CODE = 1000                          // Constant for identifying the location permission request.
    private lateinit var fusedLocationClient : FusedLocationProviderClient   // Handles location services.
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delivery_address)

        latitudeTextView = findViewById(R.id.lattidude)
        longitudeTextView = findViewById(R.id.longitude)
        providerTextView = findViewById(R.id.provider)
        countryTextView = findViewById(R.id.country)
        addressTextView = findViewById(R.id.address)

        getLocationButton = findViewById(R.id.currentLocation)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getLocationButton.setOnClickListener {
            getCurrentLocation()
        }

        // Google Maps

        val cardView = findViewById<CardView>(R.id.cardView)

        cardView.setOnClickListener {
            val intent = Intent(this, GoogleMaps::class.java)
            startActivity(intent)
        }


    }

    private fun getCurrentLocation()

    // Permission Check: Checks if location permissions are granted. If not, requests permissions from the user.
    {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_PERMISSION_REQ_CODE)
            return
        }

        // Location Request: Creates a LocationRequest object to define the parameters for location updates (interval, fastest interval, and priority).

        val locationRequest = LocationRequest.create().apply {
            interval = 1000        // means the app requests location updates every 1 second (1000 milliseconds).
            fastestInterval = 500  // fastest rate at which the app is willing to accept location updates.
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY     // LocationRequest.PRIORITY_HIGH_ACCURACY requests the most accurate location possible, which typically uses GPS,
        }


        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location : android.location.Location? = locationResult.lastLocation
                if(location!=null)
                {
                    val geocoder = Geocoder(this@DeliveryAddress, Locale.getDefault())    // Geocoder instance that converts latitude and longitude into a human-readable address.
                    val list : MutableList<Address>? = geocoder.getFromLocation(location.latitude,location.longitude,1)     // This method takes the latitude and longitude from the location object and returns a list of addresses. The 1 indicates that we only want the first matching address.



                    latitude = list?.get(0)?.latitude!!         // Gets the latitude from the first address in the list.
                    longitude = list?.get(0)?.longitude!!       // Same

                    latitudeTextView.text = "Latitude: ${latitude}"
                    longitudeTextView.text = "Longitude: ${longitude}"
                    providerTextView.text = "Provider: ${location.provider}"        // Displays the provider that gave the location (like GPS or Network).
                    countryTextView.text = "Country: ${list[0].countryName}"
                    addressTextView.text = "Address: ${list[0].getAddressLine(0)}"


                    fusedLocationClient.removeLocationUpdates(this)     // Stops receiving location updates to conserve battery after getting the desired location. This is done because we only need the current location once in this case.

                }
                else{
                    Toast.makeText(this@DeliveryAddress,"Location is null, Please try again...",
                        Toast.LENGTH_LONG).show()

                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback,null)
    }

}