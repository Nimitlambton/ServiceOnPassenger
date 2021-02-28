package com.lambtonserviceon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {


    lateinit var email : EditText
    lateinit var  password :EditText
    lateinit var loginBtn :Button
    lateinit var createAccount : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        email = findViewById(R.id.Email)
        password =  findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginbtn)
        createAccount = findViewById(R.id.CreateAcc)




        createAccount.linksClickable = true


        createAccount.setOnClickListener {

            val goto = Intent(this, RegisterActivity::class.java)
            startActivity(goto)


        }



    }

}
