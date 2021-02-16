package com.lambtonserviceon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_payment.*

class ProfileDetails : AppCompatActivity() {

    lateinit var button : Button
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)





       if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
           == PackageManager.PERMISSION_DENIED)

           ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)


        imageView = findViewById(R.id.Dispic)





        this.setupActionBarBtn()

         button = findViewById(R.id.dpBtn)
        button.setText("+");


        button.setOnClickListener {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

           startActivityForResult(cameraIntent  , 1)



        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {

            Toast.makeText(applicationContext , "hellowowld" ,  Toast.LENGTH_LONG).show()
            button.visibility = View.INVISIBLE

            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)


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
