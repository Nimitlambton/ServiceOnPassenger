package com.lambtonserviceon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() ,  View.OnClickListener{



    lateinit  var toggle : ActionBarDrawerToggle

    lateinit var button : Button
    lateinit var  postalCode : EditText



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toggle = ActionBarDrawerToggle(this , drawerlayout , R.string.open , R.string.close)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.miItem1 ->

                    Toast.makeText(this , "helloworld" ,Toast.LENGTH_SHORT).show()




            }
            true
        }



        button = findViewById(R.id.chkbtn)
        button.setOnClickListener(this)


        postalCode = findViewById(R.id.Postal)


    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){

            return true
        }
        return super.onOptionsItemSelected(item)
    }



    fun checkPostalCode(postalCode:String){


        Toast.makeText(applicationContext ,  postalCode,  Toast.LENGTH_LONG).show()


        if ( postalCode == "" ) {


            Toast.makeText(applicationContext , "please enter PostalCode  " ,  Toast.LENGTH_LONG).show()


        }



    }




    override fun onClick(v: View?) {

        when(v?.id ){


            R.id.chkbtn ->{

                checkPostalCode(postalCode.text.toString())

            }



        }








    }









}
