package com.example.easypasstickets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray
 
data class Event(
    val event_id: Int,
    val name: String,
    val description: String?,
    val price: Int,
    val img_url: String?,
    val event_date: String?
)
 
class ProductAdapter(private val eventList: List<Event>) :
    RecyclerView.Adapter<ProductAdapter.EventViewHolder>() {
 
    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.name)
        val txtDesc: TextView = itemView.findViewById(R.id.description)
        val txtPrice: TextView = itemView.findViewById(R.id.price)
        val imgProduct: ImageView = itemView.findViewById(R.id.img_url)
        val txtDate: TextView = itemView.findViewById(R.id.event_date)
        val btnPurchase: TextView = itemView.findViewById(R.id.purchase)
    }
    //Access the Layout - Single Item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_item, parent, false)
        return EventViewHolder(view)
    }
 
    //Access Views in Single Item XML and Bind Data
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.txtName.text = event.name
        holder.txtDesc.text = event.description ?: "No description"
        holder.txtDate.text = event.event_date ?: "No date provided"
        holder.txtPrice.text = "Ksh ${event.price}"
        //Change/Replace modcom2 below to your Python Anywhere username
        val imageUrl = "https://emmanuelkinda.pythonanywhere.com/static/images/${event.img_url}"
 
        //Load image using Glide, Load Faster with Glide
        Glide.with(holder.itemView.context)
            .load(imageUrl )
            .placeholder(R.drawable.ic_launcher_background) // Make sure you have a placeholder image
            .into(holder.imgProduct)
 
                //Handle Purchase Button Listener
                holder.btnPurchase.setOnClickListener {
                    val context = holder.itemView.context
                    val intent = android.content.Intent(context, PaymentActivity::class.java).apply {
                        putExtra("event_id", event.event_id)
                        putExtra("name", event.name)
                        putExtra("date", event.event_date)
                        putExtra("description", event.description)
                        putExtra("price", event.price)
                        putExtra("img_url", event.img_url)
                    }
                    context.startActivity(intent)
                }
    }
 
    override fun getItemCount(): Int = eventList.size
   //Return all products Details as a LIST
    companion object {
        fun fromJsonArray(jsonArray: JSONArray): List<Event> {
            val list = mutableListOf<Event>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)

                // Fetch and clean the event_date
                var eventDateRaw = obj.getString("event_date")
                var cleanedDate = eventDateRaw

                if (eventDateRaw.contains(",")) {
                    val afterComma = eventDateRaw.split(",")[1].trim() // Remove day name
                    val parts = afterComma.split(" ")
                    if (parts.size >= 3) {
                        cleanedDate = parts[0] + " " + parts[1] + " " + parts[2]
                    }
                }

                list.add(
                    Event(
                        event_id = obj.getInt("event_id"),
                        name = obj.getString("name"),
                        description = obj.optString("description", ""),
                        price = obj.getInt("price"),
                        event_date = cleanedDate,
                        img_url = obj.optString("img_url", "")
                    )
                )
            }
            return list
        }
    }
}
