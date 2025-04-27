package com.example.easypasstickets

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets } // End of insets

    // Find the two buttons by the use of their ids
//    val signinButton = findViewById<Button>(R.id.signinButton)
//    val signupButton = findViewById<Button>(R.id.signupButton)
      val ticketsButton = findViewById<Button>(R.id.ticketsButton)
      val aboutButton = findViewById<Button>(R.id.aboutButton)

    // Create Intent to the different activities
    // signin
//    signinButton.setOnClickListener {
//        val signinPage = Intent(applicationContext, Signin::class.java)
//        startActivity(signinPage)
//    }  // end signin intent
//
//    signupButton.setOnClickListener {
//        val signupPage = Intent(applicationContext, Signup::class.java)
//        startActivity(signupPage)
//    }    // end signup intent

        ticketsButton.setOnClickListener {
            val signupPage = Intent(applicationContext, RetrieveTicketActivity::class.java)
            startActivity(signupPage)
        }    // end ticket intent

        aboutButton.setOnClickListener {
            val aboutPage = Intent(applicationContext, About::class.java)
            startActivity(aboutPage)
        }

    // Fetch the progressbar and the recyclerView using their ids
    val progressbar = findViewById<ProgressBar>(R.id.progressbar)
    val recycleView = findViewById<RecyclerView>(R.id.recyclerView)

    // Define the url/endpoint where the products will be fetched from
    val url = "http://emmanuelkinda.pythonanywhere.com/api/getevents"

    // access the helper
    val helper = ApiHelper(applicationContext)

    // Inside the API helper, we shall access the loadProduct function
    helper.loadProducts(url, recycleView, progressbar)
    }
}