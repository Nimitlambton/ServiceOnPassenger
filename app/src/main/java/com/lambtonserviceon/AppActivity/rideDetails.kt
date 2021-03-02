package com.lambtonserviceon.AppActivity


import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.example.Example
import com.google.gson.Gson
import com.lambtonserviceon.R
import com.lambtonserviceon.dbConfig.userDetails.UserDetails
import com.lambtonserviceon.dbConfig.userDetails.userDeatailsViewModel
import com.lambtonserviceon.models.User
import okhttp3.*
import java.io.IOException


lateinit var  mapbtn :Button
lateinit var  confirmRidebtn :Button
lateinit var  Destination : EditText
lateinit var  Distance:  EditText
lateinit var EstimatedPrice : EditText
private lateinit var CurrrentUser : User
private lateinit var cu : UserDetails

private lateinit var UserDetailsViewModel: userDeatailsViewModel
//initalizing of OKHttp Client
private val client = OkHttpClient()

class rideDetails : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_details)

        //setup back button
        this.setupActionBarBtn()

        //Initializing of user data from MainActivity
        CurrrentUser = intent.getParcelableExtra("com.lambtonserviceon.models.User")

        cu = intent.getParcelableExtra("userDetails")




        UserDetailsViewModel = ViewModelProvider(this).get(userDeatailsViewModel::class.java)



//matching View with there ID
        mapbtn = findViewById(R.id.Map)
        Destination = findViewById(
            R.id.destination
        )
        Distance = findViewById(
            R.id.distance
        )
        EstimatedPrice = findViewById(
            R.id.EstimatedPrice
        )
        confirmRidebtn = findViewById(
            R.id.ConfirmRide
        )


        mapbtn.setOnClickListener {


            var intent = Intent( this , MapAct::class.java)
            intent.putExtra( "User"  , CurrrentUser)

            intent.putExtra( "UserDetails"  , cu)

            startActivity(intent)
        }

        confirmRidebtn.setOnClickListener {



            var intent = Intent( this , ConfirmRide::class.java)
            intent.putExtra( "User"  ,
                CurrrentUser
            )
            startActivity(intent)


        }


         //google Places api to fetch data of the nearest Service Ontario
        this.run("https://maps.googleapis.com/maps/api/place/textsearch/json?query=Service+Ontario+in+Toronto&location=${cu.CurrentLatititue},${cu.currentLongitude}&rankby=distance&key=AIzaSyDfitQFZjRn76sFCbB4dXzjf7r1i3GU-Lc")

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




//calling Rest Api
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

                val userDetails = UserDetails(cu.UserId,cu.FirstName,cu.LastNmae,cu.Email , cu.Password ,cu.UserImg, cu.CurrentLatititue,cu.currentLongitude , places.results[0].geometry.location.lat , places.results[0].geometry.location.lng)

                UserDetailsViewModel.update(userDetails)



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
                    Destination.setText(
                        CurrrentUser.Destination )
                    Distance.setText(distance.toString() + "   KM")
                    EstimatedPrice.setText("$ ${fare.toString()}")

                }

            }


        })





    }







}
