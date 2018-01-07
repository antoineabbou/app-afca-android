package com.example.pibbou.afca.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pibbou.afca.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.pibbou.afca.App
import com.example.pibbou.afca.repository.entity.Category


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Prepare categories used by events
        var activeCategories: ArrayList<Category> = ArrayList()
        // Get datarepo
        val dataRepository = App.sInstance!!.getDataRepository()
        // Thanks to datarepo get all events
        val allEvents = dataRepository!!.getEvents()

        // For each event get category and push it to an array
        for(event in allEvents!!){
            val category = event.category!!
            activeCategories.add(category)
        }

        // Remove duplicate categories
        val filteredCategories = activeCategories.distinct()

        // Get recyclerview View
        val recycler_view_category_list = findViewById<View>(R.id.recycler_view_category_list) as RecyclerView

        // Set fixed size
        recycler_view_category_list.setHasFixedSize(true)

        // Prepare adapter
        val adapter = CategoryListAdapter(this, allEvents, filteredCategories)

        recycler_view_category_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_category_list.adapter = adapter
    }

}
