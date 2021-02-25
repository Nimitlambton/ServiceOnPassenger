package com.lambtonserviceon

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lambtonserviceon.dbConfig.userDetails.UserDetails
import com.lambtonserviceon.dbConfig.userDetails.userDeatailsViewModel
import kotlinx.android.synthetic.main.activity_profile_details.*
import java.io.ByteArrayOutputStream


class ProfileDetails : AppCompatActivity() {

    lateinit var button : Button
    lateinit var imageView: ImageView
    lateinit var saveBtn :Button
    lateinit var  firstName : EditText
    lateinit var lastName : EditText
    var imgData : String = ""
    lateinit var Updatebtn :Button

    private lateinit var wordViewModel: userDeatailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)



       if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
           == PackageManager.PERMISSION_DENIED)

           ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)


        imageView = findViewById(R.id.Dispic)



        wordViewModel = ViewModelProvider(this).get(userDeatailsViewModel::class.java)





        this.setupActionBarBtn()

         button = findViewById(R.id.dpBtn)
        button.setText("+");


        button.setOnClickListener {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

           startActivityForResult(cameraIntent  , 1)



        }




        saveBtn = findViewById(R.id.savebtn)
        firstName = findViewById(R.id.firstName)
        lastName = findViewById(R.id.lastName)
        Updatebtn = findViewById(R.id.updatebtn)



        saveBtn.setOnClickListener {


            val firstname = firstName.text.toString()
            val lastname = lastName.text.toString()






            val userDetails = UserDetails(0,firstname,lastname,imgData)

            wordViewModel.insert(userDetails)



        }


        updatebtn.setOnClickListener {


            val intent = Intent(this, updateUserDetails::class.java)

            startActivity(intent)



        }





        wordViewModel.alldata.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {


                if(it.size !=0){



                    firstName.setText(it[0].FirstName)
                    lastName.setText(it[0].LastNmae)




                    val imgData = it[0].UserImg
                    val k =  Base64.decode(imgData,Base64.DEFAULT)
                    val image = BitmapFactory.decodeByteArray(k, 0, k.size)

                    button.visibility = View.INVISIBLE
                    imageView.setImageBitmap(image)



                    saveBtn.visibility = View.INVISIBLE




                }else{

                    updatebtn.visibility = View.INVISIBLE
                    println("user database is empty ")

                }

            }
        })




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {

            Toast.makeText(applicationContext , "hellowowld" ,  Toast.LENGTH_LONG).show()


            button.visibility = View.INVISIBLE

            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(photo)


            val byteArrayOutputStream =
                ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()


            val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)

            imgData = encoded






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
