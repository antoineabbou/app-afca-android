package com.example.pibbou.afca.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        val intent = intent
        val id = intent.getIntExtra("id", 0)

        val excerpt : TextView = findViewById(R.id.eventExcerpt)
        val dataRepository = App.sInstance!!.getDataRepository()


        /*****/

        val event = dataRepository!!.findEventById(id)

        val eventTitle: TextView = findViewById(R.id.eventTitle)
        eventTitle.setText(event?.name)

        val eventExcerpt: TextView = findViewById(R.id.eventExcerpt)
        eventExcerpt.setText(event?.excerpt)


        val eventStart: TextView = findViewById(R.id.eventStart)
        val dateStart = event!!.startingDate
        val sdf_start = SimpleDateFormat("MMM MM dd, yyyy h:mm a")
        val dateStringStart = sdf_start.format(dateStart)
        eventStart.setText(dateStringStart)


        val eventEnd: TextView = findViewById(R.id.eventEnd)
        val dateEnd = event!!.endingDate
        val sdf_end = SimpleDateFormat("MMM MM dd, yyyy h:mm a")
        val dateStringEnd = sdf_end.format(dateEnd)
        eventEnd.setText(dateStringEnd)

        // Thanks to datarepo get all events
        val place = dataRepository!!.findPlaceById(id)
        val placeText: TextView = findViewById(R.id.placeText)
        placeText.setText(place?.name)

        val category = dataRepository!!.findCategoryById(id)
        val categoryText: TextView = findViewById(R.id.categoryTitle)
        categoryText.setText(category?.name)

    }
}