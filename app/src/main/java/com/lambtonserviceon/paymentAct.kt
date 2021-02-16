package com.lambtonserviceon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class paymentAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)


       this.setupActionBarBtn()

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    fun setupActionBarBtn(){

        this.title = "Payment"
        val actionbar = supportActionBar
        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)


    }

}
