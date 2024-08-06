package com.example.tbaycity

import android.app.ActivityManager.TaskDescription
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date
import java.util.Locale


class EventDetailFragment : Fragment() {
    private lateinit var eventnewsTitle:TextView
    private lateinit var eventnewsDate:TextView
    private lateinit var eventnewsAddress:TextView
    private lateinit var eventnewsImage:ImageView
    private lateinit var eventnewsDescriptionTitle:TextView
    private lateinit var eventnewsDescription:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_detail, container, false)
        eventnewsTitle = view.findViewById(R.id.eventnews_title)
        eventnewsDate = view.findViewById(R.id.eventnews_date)
        eventnewsImage = view.findViewById(R.id.eventnews_image)
        eventnewsDescription = view.findViewById(R.id.eventnews_description)
        eventnewsAddress = view.findViewById(R.id.eventnews_address)
        eventnewsDescriptionTitle = view.findViewById(R.id.eventnews_description_title)


        if(arguments?.getString("category").toString() == "event"){
            eventnewsDescriptionTitle.text = "Event Description"
            eventnewsTitle.text = arguments?.getString("title").toString()
            eventnewsDate.text = arguments?.getString("date").toString()
            Glide.with(requireContext()).load(arguments?.getString("image")).into(eventnewsImage)
            eventnewsDescription.text = "\""+arguments?.getString("description").toString()+"\""
            eventnewsAddress.text = arguments?.getString("address").toString()
        }
        else if(arguments?.getString("category").toString() == "news"){
            reDirect(eventnewsDescriptionTitle,arguments?.getString("link").toString())
            eventnewsDescription.text = ""
            eventnewsTitle.text = arguments?.getString("title").toString()
            eventnewsDate.text = arguments?.getString("datetime").toString()
            eventnewsAddress.text = ""
            Glide.with(requireContext()).load(arguments?.getString("image").toString()).into(eventnewsImage)

        }

        return  view
    }

    private fun reDirect(eventNewsDescriptionTitle :TextView,link:String){
        val linkText = "Click here to read more"
        val spannableString = SpannableString(linkText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                widget.context.startActivity(browserIntent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true // Optional: set to false if you don't want underline
            }
        }

        spannableString.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        eventNewsDescriptionTitle.text = spannableString
        eventnewsDescriptionTitle.movementMethod = LinkMovementMethod.getInstance()
    }


}