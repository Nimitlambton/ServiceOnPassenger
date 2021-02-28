package com.lambtonserviceon

import android.content.Intent
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
import com.lambtonserviceon.AppActivity.ProfileDetails
import com.lambtonserviceon.AppActivity.paymentAct
import com.lambtonserviceon.AppActivity.rideDetails
import com.lambtonserviceon.models.User
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity()  {



    lateinit  var toggle : ActionBarDrawerToggle
    lateinit var Searchbtn : Button
    lateinit var  postalCode : EditText



    private lateinit var CurrrentUser : User



    //Ok hhtp client , used to Fetch and retrieve Rest api data
    private val client = OkHttpClient()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        //Initalizing of com.lambtonserviceon.models.User
         CurrrentUser = User()

        //View button Initialization
        Searchbtn = findViewById(R.id.Searchbtn)
        postalCode = findViewById(R.id.Postal)


        //Toggle btn for main acticity
        toggle = ActionBarDrawerToggle(this , drawerlayout , R.string.open , R.string.close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //hamburger menu button listener
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





        Searchbtn.setOnClickListener {


            //calling zipLocation Api
            this.run("  https://thezipcodes.com/api/v1/search?zipCode=${postalCode.text}&countryCode=CA&apiKey=82c04d7a7ad16e63a925ed39dd44b975")

        }




        postalCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

               checkPostalCode( postalCode.text.toString())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


    }



    //backbtn
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){

            return true
        }
        return super.onOptionsItemSelected(item)
    }



//function to check valid postal code
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



    //to fetch api zipcodes
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


                CurrrentUser.CurrentLati = mUser.location[0].latitude
                CurrrentUser.CurrentLongi =  mUser.location[0].longitude


                runOnUiThread {


                val intent = Intent(this@MainActivity, rideDetails::class.java)
                    intent.putExtra("com.lambtonserviceon.models.User" , CurrrentUser )
                startActivity(intent)

                }

            }

        }

        )

    }


}
