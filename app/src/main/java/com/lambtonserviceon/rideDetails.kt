package com.lambtonserviceon


import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.example.Example
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.lambtonserviceon.models.User
import okhttp3.*
import java.io.IOException


lateinit var  mapbtn :Button
lateinit var  confirmRidebtn :Button
lateinit var  Destination : EditText
lateinit var  Distance:  EditText
lateinit var EstimatedPrice : EditText
private lateinit var CurrrentUser : User


//initalizing of OKHttp Client

private val client = OkHttpClient()

class rideDetails : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_details)

        //setup back button
        this.setupActionBarBtn()

        println("hellowowlr")


        val firestore = Firebase.firestore





        //Initializing of user data from MainActivity
        CurrrentUser = intent.getParcelableExtra("com.lambtonserviceon.models.User")



//matching View with there ID
        mapbtn = findViewById(R.id.Map)
        Destination = findViewById(R.id.destination)
        Distance = findViewById(R.id.distance)
        EstimatedPrice = findViewById(R.id.EstimatedPrice)
        confirmRidebtn = findViewById(R.id.ConfirmRide)


        mapbtn.setOnClickListener {


            var intent = Intent( this , MapAct::class.java)
            intent.putExtra( "User"  ,  CurrrentUser  )
            startActivity(intent)
        }

        confirmRidebtn.setOnClickListener {



            var intent = Intent( this , ConfirmRide::class.java)
            intent.putExtra( "User"  ,  CurrrentUser  )
            startActivity(intent)


        }


         //google Places api to fetch data of the nearest Service Ontario
        this.run("https://maps.googleapis.com/maps/api/place/textsearch/json?query=Service+Ontario+in+Toronto&location=${CurrrentUser.CurrentLati},${CurrrentUser.CurrentLongi}&rankby=distance&key=AIzaSyDfitQFZjRn76sFCbB4dXzjf7r1i3GU-Lc")

    }


//function to setup Actionbar back button and title
    fun setupActionBarBtn(){

        this.title = "Ride Details "
        val actionbar = supportActionBar
        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
                var places =  gson.fromJson(response.body?.string(), Example::class.java)


                //Setting up destination name of current user
                CurrrentUser.Destination = places.results[0].formattedAddress



                var lati =  places.results[0].geometry.location.lat
                var longi = places.results[0].geometry.location.lng

                              //setting up lon & lat for the current user to send
                CurrrentUser.DestinationLati = lati.toString()
                CurrrentUser.Destinationlongi = longi.toString()




                //passing Destination location and the current location of the user
                val locationA = Location("point A")
                locationA.setLatitude(lati);
                locationA.setLongitude(longi);


                val locationB = Location("point B")
                locationB.setLatitude(CurrrentUser.CurrentLati.toDouble());
                locationB.setLongitude(CurrrentUser.CurrentLongi.toDouble());



                //converting distance from meters to km
                val distance  = locationB.distanceTo(locationA) / 1000;

                //funtion to calculate fare
                var fare  =  distance * 10


                //setting up on ui thats why runing on diffrent thread of UI
                runOnUiThread{
                    Destination.setText(CurrrentUser.Destination )
                    Distance.setText(distance.toString() + "   KM")
                    EstimatedPrice.setText("$ ${fare.toString()}")

                }

            }


        })





    }







}
