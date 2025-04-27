package com.example.easypasstickets

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets } // end of insets

        // Find the four Edit texts and one Button by use of their ids
        val emailEdit = findViewById<EditText>(R.id.email)
        val usernameEdit = findViewById<EditText>(R.id.username)
        val phoneEdit = findViewById<EditText>(R.id.phone)
        val passwordEdit = findViewById<EditText>(R.id.password)

        val registerButton = findViewById<Button>(R.id.signupButton)

        // Handle the events that will happen when the signup button is clicked
        registerButton.setOnClickListener{
            // specify the API endpoint
            val api = "https://emmanuelkinda.pythonanywhere.com/api/signup"

            // create a form data object that will hold the submitted info
            val data = RequestParams()

            // add the data to the new form data
            data.put("email", emailEdit.text.toString().trim())
            data.put("username", usernameEdit.text.toString().trim())
            data.put("phone", phoneEdit.text.toString().trim())
            data.put("password", passwordEdit.text.toString())

            // Access the API helper
            val helper = ApiHelper(applicationContext)

            // Access the post method/function inside of the helper class
            helper.post(api, data)
        }
    }
}