package com.lambtonserviceon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ConfirmRide : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_ride)

        this.setupActionBarBtn()



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




}
