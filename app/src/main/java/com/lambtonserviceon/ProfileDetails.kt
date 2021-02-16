package com.lambtonserviceon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_payment.*

class ProfileDetails : AppCompatActivity() {

    lateinit var button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)



        this.setupActionBarBtn()

         button = findViewById(R.id.dpBtn)


        button.setText("+");


        button.setOnClickListener {


            Toast.makeText(this, "Dummy", Toast.LENGTH_SHORT).show()


        }


    }











    fun setupActionBarBtn(){

        this.title = "Profile Details"
        val actionbar = supportActionBar
        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)



    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
