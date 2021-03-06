package com.lambtonserviceon.AppActivity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.lambtonserviceon.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.maps.android.PolyUtil
import com.lambtonserviceon.models.directions.Direction
import com.lambtonserviceon.models.directions.Step
import okhttp3.*
import java.io.IOException
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

private lateinit var status :TextView
private lateinit var Drivername :TextView

//Google map initialization
private lateinit var mMap: GoogleMap
private lateinit var myMarker: Marker

private val client = OkHttpClient()
private lateinit var  decodedPolyLine : List <LatLng>
class ConfirmRide : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private var driverLocation = LatLng(0.0 , 0.0)

    //Current location
    private var riderlocation = LatLng(0.0 , 0.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_ride)

        this.setupActionBarBtn()

        //setting up Googlemap
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        status = findViewById(R.id.status)
        Drivername  =  findViewById(R.id.dname)



    }
    

    //function to setup Actionbar back button and title
    fun setupActionBarBtn(){

        this.title = "Confirm Ride"
        val actionbar = supportActionBar
        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {


        val db = Firebase.firestore

        val docRef = db.collection("ridedetails").document("ride").collection("driverDetails").document("details" )
        db.collection("ridedetails").document("ride")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                println("DRIVER DETAILS ERR")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {

                println("DRIVER DETAILS SUCCESS")

                status.setText("Drive Found")
                Drivername.setText(snapshot.get("firstName").toString() )
                println("Current data: ${snapshot.get("firstName") }")

                val lati  =  snapshot.get("ridersLatititue").toString()
                val longi =  snapshot.get("ridersLongitude").toString()

                riderlocation = LatLng(lati.toDouble() , longi.toDouble())

                val Driverlati  =  snapshot.get("currentLatititue").toString()
                val Driverlongi =  snapshot.get("currentLongitude").toString()
                driverLocation = LatLng(Driverlati.toDouble() , Driverlongi.toDouble())


                println("you guys !! its locations........")
                //println("riderloc"+ riderlocation)
                println("driverloc"+driverLocation)




                mMap = googleMap
                mMap.clear()
                myMarker = mMap.addMarker(MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)) .position(driverLocation).title("yourDriver"))
                myMarker.showInfoWindow()


                myMarker =
                    mMap.addMarker(MarkerOptions() .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .position(riderlocation).title("hey you are here"))

                mMap?.animateCamera(CameraUpdateFactory.newLatLng(driverLocation))
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(driverLocation, 10f))



                val url = getURL(driverLocation, riderlocation)
                println(url)
                this.run(url)





            } else {


            }
        }







    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("Not yet implemented")
    }


    //build Url to fetch google api
    private fun getURL(from: LatLng, to: LatLng): String {

        val origin = "origin=" + from.latitude + "," + from.longitude
        val dest = "destination=" + to.latitude + "," + to.longitude
        val sensor = "sensor=false"
        val params = "$origin&$dest"
        val key = "&key=AIzaSyDfitQFZjRn76sFCbB4dXzjf7r1i3GU-Lc"

        return "https://maps.googleapis.com/maps/api/directions/json?$params$key"

    }

    fun run(url: String) {

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {


            override fun onFailure(call: Call, e: IOException) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

            override fun onResponse(call: Call, response: Response) {

                val gson = Gson()
                var Direction2 = gson.fromJson(response.body?.string(), Direction::class.java)

                //function to fetch steps and pass to ADD polyline
                addPolyLines(Direction2.routes[0].legs[0].steps)
            }

        })
    }


    private fun addPolyLines(steps: List<Step>) {

        val path: MutableList<List<LatLng>> = ArrayList()

        for (step in steps) {
            decodedPolyLine = PolyUtil.decode(step.polyline.points);
            path.add(decodedPolyLine)

        }

        runOnUiThread {

            val  polyLineOption = PolylineOptions()

            polyLineOption.color(Color.GREEN)

            for(p in path)
                polyLineOption.addAll(p)

            val polyline = mMap.addPolyline(polyLineOption)

        }


    }
}
