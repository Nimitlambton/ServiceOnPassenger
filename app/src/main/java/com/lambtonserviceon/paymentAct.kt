package com.lambtonserviceon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.labtest1.feeskeeper.nimit.dbConfig.cardDetailsViewMOdel
import com.lambtonserviceon.dbConfig.CardDetails.CardDetails
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_profile_details.*

class paymentAct : AppCompatActivity() {


    private lateinit var wordViewModel: cardDetailsViewMOdel
    private lateinit var saveBtn : Button
    private lateinit var cardno :EditText
    private lateinit var expiryno :EditText
    private lateinit var update :Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)




        wordViewModel = ViewModelProvider(this).get(cardDetailsViewMOdel::class.java)



        cardno = findViewById(R.id.cardno)
        expiryno = findViewById(R.id.expiryDate)
        update = findViewById(R.id.updateCardBtn)



        wordViewModel.alldata.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {


                if(it.size !=0){

                    cardno.setText(it[0].cardNumber.toString())
                    expiryno.setText(it[0].cvv.toString())

                    saveBtn.visibility = View.INVISIBLE
                   // update.visibility = in

                }else{

                    update.visibility = View.INVISIBLE
                    println("user database is empty ")




                }

            }
        })




       this.setupActionBarBtn()


        saveBtn = findViewById(R.id.savebtn)

        saveBtn.setOnClickListener {






            if  (cardno.text.toString() == "" || expiryno.text.toString() == "" ) {


                Toast.makeText(this, "please enter card details", Toast.LENGTH_SHORT).show()


            }else {



                val cardNumber = cardno.text.toString().toDouble()
                val expiryNumber =  expiryno.text.toString().toInt()

                var cardDetails  = CardDetails(0, cardNumber, expiryNumber)

                wordViewModel.insert(cardDetails)

                println("dataSaved")

            }




        }



        update.setOnClickListener {


            val intent = Intent(this, updateCardDetails::class.java)

            startActivity(intent)



        }



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
