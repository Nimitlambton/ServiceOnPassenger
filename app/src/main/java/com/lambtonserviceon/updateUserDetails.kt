package com.lambtonserviceon

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.labtest1.feeskeeper.nimit.dbConfig.cardDetailsViewMOdel
import com.lambtonserviceon.dbConfig.userDetails.UserDetails
import com.lambtonserviceon.dbConfig.userDetails.userDeatailsViewModel
import kotlinx.android.synthetic.main.activity_update_user_details.*
import java.io.ByteArrayOutputStream

class updateUserDetails : AppCompatActivity() {



    private lateinit var wordViewModel: userDeatailsViewModel
    private  lateinit var firstName  : EditText
    private  lateinit  var lastname : EditText
    private  lateinit  var update : Button
    private  lateinit  var changeBtn : Button
    private  lateinit  var Img : ImageView

    var imgData : String = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user_details)


        var Id :Int = 0
        this.title = "update user details "

        firstName = findViewById(R.id.FirstName)
        lastname = findViewById(R.id.LastName)

        changeBtn = findViewById(R.id.changebtn)

        wordViewModel = ViewModelProvider(this).get(userDeatailsViewModel::class.java)

        Img = findViewById(R.id.img)

        update = findViewById(R.id.updatebtn)

        wordViewModel.alldata.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {



                if(it.size !=0){

                    firstName.setText(it[0].FirstName)


                    lastname.setText(it[0].LastNmae)

                    //Id = it[0].cardId

                    val imgData = it[0].UserImg
                    val k =  Base64.decode(imgData, Base64.DEFAULT)
                    val image = BitmapFactory.decodeByteArray(k, 0, k.size)

                    this.Img.setImageBitmap(image)

                    Id =it[0].UserId


                }else{

                    println("user database is empty ")


                }

            }
        })




        updatebtn.setOnClickListener {


            var firstname = FirstName.text.toString()
            var lastname = LastName.text.toString()



            val userDetails = UserDetails(Id,firstname,lastname,imgData)

            wordViewModel.update(userDetails)
            finish()



        }




        changeBtn.setOnClickListener {


            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            startActivityForResult(cameraIntent  , 1)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {

            Toast.makeText(applicationContext , "hellowowld" ,  Toast.LENGTH_LONG).show()




            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            this.Img.setImageBitmap(photo)


            val byteArrayOutputStream =
                ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()


            val encoded: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
            imgData = encoded





        }
    }











}
