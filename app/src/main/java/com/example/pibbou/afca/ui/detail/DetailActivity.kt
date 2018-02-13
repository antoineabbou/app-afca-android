package com.example.pibbou.afca.ui.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import java.text.SimpleDateFormat

/**
 * Created by antoineabbou on 08/01/2018.
 */
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        // get id on click
        val intent = intent
        val id = intent.getIntExtra("id", 0)

        // data repository call
        val dataRepository = App.sInstance!!.getDataRepository()


        /*****/

        // Find the event, the place and the category in the list
        val event = dataRepository!!.findEventById(id)
        val place = dataRepository!!.findPlaceById(id)
        val category = dataRepository!!.findCategoryById(id)


        // Loading datas : Title, excerpt, start, end, place and category


        // EVENT

        // Title
        val eventTitle: TextView = findViewById(R.id.eventTitle)
        eventTitle.setText(event?.name)


        // Excerpt
        val eventExcerpt: TextView = findViewById(R.id.eventExcerpt)
        eventExcerpt.setText(event?.excerpt)


        // Start
        val eventStart: TextView = findViewById(R.id.eventStart)
        val dateStart = event!!.startingDate
        val sdf_start = SimpleDateFormat("MMM MM dd, yyyy h:mm a") // Adapting format
        val dateStringStart = sdf_start.format(dateStart)
        eventStart.setText(dateStringStart)


        // End
        val eventEnd: TextView = findViewById(R.id.eventEnd)
        val dateEnd = event!!.endingDate
        val sdf_end = SimpleDateFormat("MMM MM dd, yyyy h:mm a") // Adapting format
        val dateStringEnd = sdf_end.format(dateEnd)
        eventEnd.setText(dateStringEnd)


        // PLACE


        val placeText: TextView = findViewById(R.id.placeText)
        placeText.setText(place?.name)


        val categoryText: TextView = findViewById(R.id.categoryTitle)
        categoryText.setText(category?.name)

    }
}