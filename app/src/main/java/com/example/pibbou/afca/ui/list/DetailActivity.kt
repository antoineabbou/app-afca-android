package com.example.pibbou.afca.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.DataRepository
import com.example.pibbou.afca.repository.entity.Category
import com.example.pibbou.afca.repository.entity.Place

/**
 * Created by antoineabbou on 08/01/2018.
 */
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        val id = intent.getIntExtra("id", 0)

        val dataRepository = App.sInstance!!.getDataRepository()
        // Thanks to datarepo get all events
        val place = dataRepository!!.findPlaceById(id)

        val placeName = place?.name

        val placeText: TextView = findViewById(R.id.eventTitle)

        placeText.setText(placeName);


    }
}