package com.lambtonserviceon


import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

import com.lambtonserviceon.models.User



private lateinit var CurrrentUser : User

class MapAct : AppCompatActivity() , OnMapReadyCallback ,GoogleMap.OnMarkerClickListener{







    private lateinit var mMap: GoogleMap
    private lateinit var myMarker : Marker
    private var PERTH = LatLng(-31.952854, 115.857342)
    private var title2 = "blue"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)




        this.title = "Maps"



        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        //Initializing of user data from MainActivity
        CurrrentUser = intent.getParcelableExtra("User" )
//
//        println("this is  current location" )
//        println(CurrrentUser.CurrentLati)
//        println(CurrrentUser.CurrentLati)
//        println("this is  destination  location" )
//        println(CurrrentUser.Destinationlongi)
//        println(CurrrentUser.DestinationLati)








    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.clear()



        var PERTH = LatLng(CurrrentUser.CurrentLati.toDouble(),CurrrentUser.CurrentLongi.toDouble())






        mMap?.animateCamera(CameraUpdateFactory.newLatLng(PERTH))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(PERTH, 10f))
        myMarker = mMap.addMarker(MarkerOptions().position(PERTH).title("hey you are here"))
        myMarker.showInfoWindow()



        var DestinationAnontation = LatLng(CurrrentUser.DestinationLati  .toDouble(),CurrrentUser.Destinationlongi  .toDouble())


        myMarker = mMap.addMarker(MarkerOptions().position(DestinationAnontation).title("hey you want to go here"))






        val polyline = mMap.addPolyline(PolylineOptions().add(PERTH ,DestinationAnontation) )





    }



    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}




