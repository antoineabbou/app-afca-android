package com.example.pibbou.afca.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.pibbou.afca.R

/**
 * Created by antoineabbou on 08/01/2018.
 */
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        val id = intent.getIntExtra("id", 0)

        val title: TextView = findViewById(R.id.eventTitle)
        val excerpt : TextView = findViewById(R.id.eventExcerpt)


    }
}