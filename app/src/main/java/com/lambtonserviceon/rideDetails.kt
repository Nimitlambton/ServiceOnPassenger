package com.lambtonserviceon

import User
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.example.Example
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


lateinit var  search :Button
lateinit var  Destination : EditText

private val client = OkHttpClient()
class rideDetails : AppCompatActivity() {


    private lateinit var CurrrentUser : User
    private val exception: Exception? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_details)
        this.setupActionBarBtn()



        //user data from MainActivity

        CurrrentUser = intent.getParcelableExtra("User")




        search = findViewById(R.id.Map)


      Destination = findViewById(R.id.destination)









        search.setOnClickListener {







        }


         //google Api

        this.run("https://maps.googleapis.com/maps/api/place/textsearch/json?query=Service+Ontario+in+Toronto&location=${CurrrentUser.lati},${CurrrentUser.longi}&rankby=distance&key=AIzaSyDfitQFZjRn76sFCbB4dXzjf7r1i3GU-Lc")










    }





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



//                println( response.body?.string())



                val gson = Gson()

                var mUser =  gson.fromJson(response.body?.string(), Example::class.java)


                 println(mUser.results[0].formattedAddress)
                println(mUser.results[0].geometry.location.lat)
                println(mUser.results[0].geometry.location.lng)

                CurrrentUser.Destination = mUser.results[0].formattedAddress


                println(CurrrentUser.Destination)


                runOnUiThread {

                    Destination.setText(CurrrentUser.Destination )

                }


            }


        })
    }







}
