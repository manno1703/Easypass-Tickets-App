package com.example.easypasstickets

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets }

    // Retrive the data passed from the previous activity and store them in a variable
    val name = intent.getStringExtra("name")
    val cost = intent.getIntExtra("price", 0)
    val photo = intent.getStringExtra("img_url")

    // Find the views - product name and product cost using their ids
    val txtName : TextView = findViewById(R.id.textEventName)
    val txtCost : TextView =  findViewById(R.id.textEventCost)
    val imgProduct = findViewById  <ImageView> (R.id.imgEvent)

    // Update the textviews with the data extracted from the putExtra
    txtName.text= name
    txtCost.text= "Kes $cost"

        val imageUrl = "https://emmanuelkinda.pythonanywhere.com/static/images/$photo"
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(imgProduct)


        // Find the pay button and set a Click listener to it
     val BtnPay = findViewById<Button>(R.id.pay)
     val editPhone = findViewById<EditText>(R.id.phone)

     BtnPay.setOnClickListener {
        // specify the endpoint
         val api = "https://emmanuelkinda.pythonanywhere.com/api/mpesa_payment"

         // Get the phone number and convert it to a string
         val phone = editPhone.text.toString().trim()

         // create a new form data object that will enable you hold the phone number and the amount
         val data = RequestParams()

         data.put("amount", cost)
         data.put("phone", phone)

       // Access/import the helper
         val helper = ApiHelper(applicationContext)

         // Access the function POST inside the APIHelper class
         helper.post(api, data)
     }


    }
}