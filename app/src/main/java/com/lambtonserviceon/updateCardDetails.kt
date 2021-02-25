package com.lambtonserviceon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.labtest1.feeskeeper.nimit.dbConfig.cardDetailsViewMOdel
import androidx.lifecycle.Observer
import com.lambtonserviceon.dbConfig.CardDetails.CardDetails
import kotlinx.android.synthetic.main.activity_payment.view.*

class updateCardDetails : AppCompatActivity() {
    private lateinit var wordViewModel: cardDetailsViewMOdel

    var Id :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_card_details)


        this.title = "updateDetails"


        val cardnumber : EditText
        val cvv :       EditText
        val updatebtn :Button





        cardnumber = findViewById(R.id.cardno)
        cvv = findViewById(R.id.cvv)
        updatebtn = findViewById(R.id.updatebtn)


        wordViewModel = ViewModelProvider(this).get(cardDetailsViewMOdel::class.java)



        wordViewModel.alldata.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {




                if(it.size !=0){

                    cardnumber.setText(it[0].cardNumber.toString())


                    cvv.setText(it[0].cardId.toString()

                    )

                     Id = it[0].cardId




                }else{


                    println("user database is empty ")




                }

            }
        })




        updatebtn.setOnClickListener {




            val cardNumber = cardnumber.text.toString().toDouble()
           val expiryNumber =  cvv.text.toString().toInt()

            var cardDetails  = CardDetails(this.Id, cardNumber, expiryNumber)

            wordViewModel.update(cardDetails)

            println("dataupdated")



             finish()





        }



    }


}
