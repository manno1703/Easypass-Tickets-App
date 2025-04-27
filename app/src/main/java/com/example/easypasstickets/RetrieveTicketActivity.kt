package com.example.easypasstickets

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class RetrieveTicketActivity : AppCompatActivity() {

    private lateinit var inputPhone: EditText
    private lateinit var btnSearch: Button
    private lateinit var ticketResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve_ticket)

        inputPhone = findViewById(R.id.inputPhone)
        btnSearch = findViewById(R.id.btnSearch)
        ticketResult = findViewById(R.id.ticketResult)

        btnSearch.setOnClickListener {
            val phone = inputPhone.text.toString().trim()
            if (phone.isNotEmpty()) {
                getTicket(phone)
            } else {
                Toast.makeText(this, "Enter a phone number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTicket(phone: String) {
        val url = "https://emmanuelkinda.pythonanywhere.com/api/get_ticket"

        val queue = Volley.newRequestQueue(this)

        val request = object : StringRequest(Method.POST, url, { response ->
            try {
                val jsonObject = org.json.JSONObject(response)

                if (jsonObject.has("tickets")) {
                    val ticketsArray = jsonObject.getJSONArray("tickets")
                    val stringBuilder = StringBuilder()

                    for (i in 0 until ticketsArray.length()) {
                        val ticket = ticketsArray.getJSONObject(i)
                        val customerName = ticket.getString("customer_name")
                        val eventName = ticket.getString("event_name")

                        stringBuilder.append("🎟️ Ticket #${i + 1}\n")
                        stringBuilder.append("👤 Name: $customerName\n")
                        stringBuilder.append("🎉 Event: $eventName\n\n")
                    }

                    ticketResult.text = stringBuilder.toString()
                } else if (jsonObject.has("message")) {
                    ticketResult.text = jsonObject.getString("message")
                }

            } catch (e: Exception) {
                ticketResult.text = "Error parsing ticket data"
            }
        }, { error ->
            ticketResult.text = "Error fetching ticket: ${error.message}"
        }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["phone"] = phone
                return params
            }
        }

        queue.add(request)
    }
}
