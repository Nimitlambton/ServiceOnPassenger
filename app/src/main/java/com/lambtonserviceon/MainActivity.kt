package com.lambtonserviceon

import User
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() ,  View.OnClickListener , LocationListener {



    lateinit  var toggle : ActionBarDrawerToggle
    lateinit var button : Button
    lateinit var Searchbtn : Button
    lateinit var  postalCode : EditText

    private lateinit var locationManager: LocationManager
    private lateinit var tvGpsLocation: TextView


    private lateinit var CurrrentUser : User


    val locationPermissionCode = 2




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Initalizing of User
         CurrrentUser = User()

        toggle = ActionBarDrawerToggle(this , drawerlayout , R.string.open , R.string.close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        navView.setNavigationItemSelectedListener {
//
//
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
        button = findViewById(R.id.chkbtn)
        button.setOnClickListener(this)
        Searchbtn.setOnClickListener(this)

        postalCode = findViewById(R.id.Postal)


        this.getLocation()




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

            Toast.makeText(applicationContext , "please enter PostalCode  " ,  Toast.LENGTH_LONG).show()
        }
            var    pattern  =  Regex  ("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$")
            var result = pattern.matches(postalCode)

        if (result) {
        //    Toast.makeText(applicationContext , result.toString() ,  Toast.LENGTH_LONG).show()
            Searchbtn.visibility = View.VISIBLE
        }else if (result == false )  {
            Toast.makeText(applicationContext , "Invalid Postal Code" ,  Toast.LENGTH_LONG).show()
            Searchbtn.visibility = View.INVISIBLE
        }
    }








    override fun onClick(v: View?) {

        when(v?.id ){

            R.id.chkbtn ->{
                checkPostalCode(postalCode.text.toString())
            }

            R.id.Searchbtn -> {





                val intent = Intent(this, rideDetails::class.java)


               intent.putExtra("User" , this.CurrrentUser )

                startActivity(intent)


            }


        }

    }






    private fun getLocation( ) {


        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)




    }








    override fun onLocationChanged(location: Location) {

        this.CurrrentUser.lati = location.latitude.toString()
        this.CurrrentUser.longi =  location.longitude.toString()

      //  Toast.makeText(this, "Latitude: " + location.latitude.toString() + " , Longitude: " + location.longitude.toString(), Toast.LENGTH_SHORT).show()







    }




    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }











}
