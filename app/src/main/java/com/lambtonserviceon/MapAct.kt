package com.lambtonserviceon
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import com.google.maps.android.PolyUtil
import com.lambtonserviceon.models.User
import com.lambtonserviceon.models.directions.Direction
import com.lambtonserviceon.models.directions.Step
import okhttp3.*
import java.io.IOException



private lateinit var CurrrentUser : User
private val client = OkHttpClient()
private lateinit var  decodedPolyLine : List <LatLng>
private lateinit var mMap: GoogleMap
private lateinit var myMarker: Marker

class MapAct : AppCompatActivity() , OnMapReadyCallback ,GoogleMap.OnMarkerClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)




        this.title = "Maps"


        CurrrentUser = intent.getParcelableExtra("User")


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        this.setupActionBarBtn()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    fun setupActionBarBtn(){

        this.title = "MapAct"
        val actionbar = supportActionBar
        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)
    }


    override fun onMapReady(googleMap: GoogleMap) {


        mMap = googleMap
        mMap.clear()

        var DestinationAnontation = LatLng(
            CurrrentUser.DestinationLati.toDouble(),
            CurrrentUser.Destinationlongi.toDouble())

        var PERTH = LatLng(CurrrentUser.CurrentLati.toDouble(), CurrrentUser.CurrentLongi.toDouble())


        mMap?.animateCamera(CameraUpdateFactory.newLatLng(PERTH))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(PERTH, 10f))
        myMarker = mMap.addMarker(MarkerOptions().position(PERTH).title("hey you are here"))
        myMarker.showInfoWindow()


        myMarker =
            mMap.addMarker(MarkerOptions().position(DestinationAnontation).title("hey you want to go here"))



        val url = getURL(PERTH, DestinationAnontation)


        this.run(url)

    }


    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun getURL(from: LatLng, to: LatLng): String {

        val origin = "origin=" + from.latitude + "," + from.longitude
        val dest = "destination=" + to.latitude + "," + to.longitude
        val sensor = "sensor=false"
        val params = "$origin&$dest"
        val key = "&key=AIzaSyDfitQFZjRn76sFCbB4dXzjf7r1i3GU-Lc"


        println("https://maps.googleapis.com/maps/api/directions/json?$params$key")
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

                println("helloworld.............")
                println(Direction2.routes[0].legs[0].steps)
                addPolyLines(Direction2.routes[0].legs[0].steps)
            }

        })
    }

    private fun addPolyLines(steps: List<Step>) {

        val path: MutableList<List<LatLng>> = ArrayList()

        for (step in steps) {
             decodedPolyLine = PolyUtil.decode(step.polyline.points);


            println("hellowowlr-------")
            println(decodedPolyLine)
            path.add( decodedPolyLine)



        }




        runOnUiThread {

           val  polyopt = PolylineOptions()

            for(p in path)
            polyopt.addAll(p)

            val polyline = mMap.addPolyline(polyopt)

        }


    }
}




