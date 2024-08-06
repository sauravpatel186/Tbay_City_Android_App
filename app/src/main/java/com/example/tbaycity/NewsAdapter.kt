package com.example.tbaycity

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
class NewsAdapter(private val newsList:ArrayList<News>,private val onItemClicked: (News) -> Unit): RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout_events,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news:News = newsList[position]

        if (news.eventImageLink=="null"){
            Log.d("news",news.eventImageLink)
            news.eventImageLink = "https://placehold.co/600x400/png"
        }
        Glide.with(holder.eventImage.context).load(news.eventImageLink).centerCrop().into(holder.eventImage)
//        holder.eventDescription.text = event.description
        holder.eventTitle.text = news.title
        val timestamp = news.dateTime
        val date = timestamp?.toDate()
        holder.eventDate.text = date.toString()
//        holder.eventDate.text = parseFirebaseTimestamp(news.dateTime)
        holder.itemView.setOnClickListener {
            onItemClicked(news)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val eventImage = itemView.findViewById<ImageView>(R.id.event_image)
        val eventTitle = itemView.findViewById<TextView>(R.id.event_title)
        //        val eventDescription = itemView.findViewById<TextView>(R.id.event_description)
        val eventDate = itemView.findViewById<TextView>(R.id.event_date)
    }


    fun formatDate(inputDateString: String): String {

        val inputFormat = SimpleDateFormat("EEEE, MMMM d, yyyy - h:mm a 'to' h:mm a", Locale.ENGLISH)

        val outputFormat = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH)

        return try {

            val date = inputFormat.parse(inputDateString)

            date?.let { outputFormat.format(it) } ?: "Invalid Date"
        } catch (e: Exception) {
            "Error formatting date"
        }
    }
    fun parseFirebaseTimestamp(timestamp: Timestamp?): String {
        val date = timestamp?.toDate()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return date?.let { sdf.format(it) } ?: "No date available"
    }
}
