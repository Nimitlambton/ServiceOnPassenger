package com.lambtonserviceon

import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.gson.Gson
import com.lambton.GetZipCode
import com.lambtonserviceon.models.User
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() ,  View.OnClickListener {



    lateinit  var toggle : ActionBarDrawerToggle
    lateinit var Searchbtn : Button
    lateinit var  postalCode : EditText


    private lateinit var locationManager: LocationManager


    private lateinit var CurrrentUser : User


    val locationPermissionCode = 2

    private val client = OkHttpClient()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        //Initalizing of com.lambtonserviceon.models.User
         CurrrentUser = User()



        toggle = ActionBarDrawerToggle(this , drawerlayout , R.string.open , R.string.close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        //hamburger menu button
        navView.setNavigationItemSelectedListener {

            when(it.itemId){


                R.id.miItem1 -> {
                    val intent = Intent(this, ProfileDetails::class.java)

                startActivity(intent)
                }

                R.id.miItem2 -> {



                    val intent = Intent(this, paymentAct::class.java)

                    startActivity(intent)

                }

                R.id.miItem3 -> {
                    Toast.makeText(this, "Dummy", Toast.LENGTH_SHORT).show()
                }

            }


            true
        }



        Searchbtn = findViewById(R.id.Searchbtn)



        postalCode = findViewById(R.id.Postal)




        Searchbtn.setOnClickListener(this)






        //this.getLocation()






        postalCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

               checkPostalCode( postalCode.text.toString())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

              //  checkPostalCode( postalCode.text.toString())



            }
        })


    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){

            return true
        }
        return super.onOptionsItemSelected(item)
    }



    fun checkPostalCode(postalCode:String){

        if ( postalCode == "" ) {
            return
        }
            var    pattern  =  Regex  ("^\\d{5}-\\d{4}|\\d{5}|[A-Z]\\d[A-Z] \\d[A-Z]\\d\$")

            var result = pattern.matches(postalCode)

        if (result) {

            Searchbtn.visibility = View.VISIBLE
        }else if (result == false )  {

            Searchbtn.visibility = View.INVISIBLE
        }
    }




    override fun onClick(v: View?) {
        when(v?.id ){

            R.id.Searchbtn -> {

                this.run("  https://thezipcodes.com/api/v1/search?zipCode=${postalCode.text}&countryCode=CA&apiKey=82c04d7a7ad16e63a925ed39dd44b975")

            }

        }

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

                val mUser =  gson.fromJson(response.body?.string(), GetZipCode::class.java)

//
//                println( mUser.location[0].City)
//               println(mUser.location[0].latitude)
//                println(mUser.location[0].longitude)


                CurrrentUser.CurrentLati = mUser.location[0].latitude
                CurrrentUser.CurrentLongi =  mUser.location[0].longitude


                runOnUiThread {
//
//                    println("sent" + CurrrentUser.lati)
//                    println( CurrrentUser.longi)

                val intent = Intent(this@MainActivity, rideDetails::class.java)
                    intent.putExtra("com.lambtonserviceon.models.User" , CurrrentUser )
                startActivity(intent)

                }

            }

        }

        )

    }







//    private fun getLocation( ) {
//
//
//        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
//
//
//
//
//    }
//
//
//    override fun onLocationChanged(location: Location) {
////
////        this.CurrrentUser.lati = location.latitude.toString()
////        this.CurrrentUser.longi =  location.longitude.toString()
//
//      //  Toast.makeText(this, "Latitude: " + location.latitude.toString() + " , Longitude: " + location.longitude.toString(), Toast.LENGTH_SHORT).show()
//
//
//    }
//
//    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onProviderEnabled(provider: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onProviderDisabled(provider: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        if (requestCode == locationPermissionCode) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
//            }
//            else {
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//















}
