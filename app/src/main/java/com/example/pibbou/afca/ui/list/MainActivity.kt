package com.example.pibbou.afca.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pibbou.afca.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.pibbou.afca.App
import com.example.pibbou.afca.repository.entity.Category
import android.widget.AdapterView
import com.example.pibbou.afca.R.id.spinner
import android.R.array
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pibbou.afca.repository.entity.Event
import android.widget.Toast
import android.widget.AdapterView.OnItemSelectedListener




class MainActivity : AppCompatActivity() {

    private var eventsByDay: ArrayList<Event>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEventsList()
        setupDay()
    }


    private fun setupEventsList() {
        // Prepare categories used by events
        var activeCategories: ArrayList<Category> = ArrayList()
        // Get datarepo
        val dataRepository = App.sInstance!!.getDataRepository()
        // Thanks to datarepo get all events
        eventsByDay = dataRepository!!.getEvents()

        // For each event get category and push it to an array
        for(event in eventsByDay!!){
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
        val adapter = CategoryListAdapter(this, eventsByDay, filteredCategories)

        recycler_view_category_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_category_list.adapter = adapter
    }


    private fun setupDay() {

        val adapter = ArrayAdapter.createFromResource(
                this,
                R.array.sort_day,
                android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = findViewById<View>(R.id.spinner) as Spinner

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {

                val item = adapterView.getItemAtPosition(position)
                if (item != null) {
                    Toast.makeText(this@MainActivity, item.toString(),
                            Toast.LENGTH_SHORT).show()
                }
                /*Toast.makeText(this@MainActivity, "Selected",
                        Toast.LENGTH_SHORT).show()
*/
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }

        /*spinner.onItemSelectedListener

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                if (position == 0) {
                    //                    sortByName();
                } else {
                    //                    sortByPrice();
                }
                laptopsAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        })*/
    }

}
