package com.example.easypasstickets

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class Signin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets } // end insets

        // Find the edit texts by use of their ids
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        // Find the button by use of its id
        val loginButton = findViewById<Button>(R.id.signinButton)

        // Implement the functionality to occur when the login button is clicked.
        loginButton.setOnClickListener {
            // Specify the API endpoint for login
            val api = "https://emmanuelkinda.pythonanywhere.com/api/signin"

            // Create a form data object that will hold both the email and the password entered
            val data = RequestParams()

            // Append the email and the password to the form data
            data.put("email", email.text.toString().trim())
            data.put("password", password.text.toString().trim())

            // Access the ApiHelper
            val helper = ApiHelper(applicationContext)

            // Access the post_login function inside of the helper
            helper.post_login(api, data)
        }

    }
}