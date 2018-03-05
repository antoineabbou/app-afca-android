package com.example.pibbou.afca.ui.fragment

/**
 * Created by antoineabbou on 11/01/2018.
 */

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.MainThread
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.DataStore
import com.example.pibbou.afca.repository.FavoriteManager
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.list.CategoryListAdapter
import com.example.pibbou.afca.ui.list.FilterListAdapter

class FragmentHome : Fragment() {

    private lateinit var view1: View

    // Prepare eventsByDay array
    private lateinit var recycler_view_category_list: RecyclerView

    private lateinit var recycler_view_filter_list: RecyclerView


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view1  = inflater!!.inflate(R.layout.fragment_home, container, false)

        setupEventsList()
        setupDay()
        setupFilterList()


        return view1
    }

    private fun setupEventsList() {

        // Get recyclerview View
        recycler_view_category_list = view1.findViewById<View>(R.id.recycler_view_category_list) as RecyclerView

        // Set fixed size
        recycler_view_category_list.setHasFixedSize(true)

        // Prepare adapter
        DataStore.categoriesAdapter = CategoryListAdapter(this.context, DataStore.currentEvents)

        recycler_view_category_list.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_category_list.adapter = DataStore.categoriesAdapter
    }


    private fun setupFilterList() {

        // Get recyclerview View
        recycler_view_filter_list = view1.findViewById<View>(R.id.recycler_view_filter_list) as RecyclerView

        // Set fixed size
        recycler_view_filter_list.setHasFixedSize(true)

        // Prepare adapter
        DataStore.filterAdapter = FilterListAdapter(this.context, DataStore.currentFilters)

        recycler_view_filter_list.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_filter_list.isNestedScrollingEnabled = false
        // Set adapter
        recycler_view_filter_list.adapter = DataStore.filterAdapter
    }


    private fun setupDay() {

        val adapter = ArrayAdapter.createFromResource(
                this.context,
                R.array.sort_day,
                android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = view1.findViewById<View>(R.id.spinner) as Spinner

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>, view: View?, position: Int, id: Long) {

                val item = adapterView.getItemAtPosition(position)
                if (item != null) {
                    DataStore.day = position
                    DataStore.updateEventDatas(position, 0)
                    DataStore.setFilter()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }

    }

    // TODO Implement methods of EventListAdapter here --> Get Favorites, then replace all
    // TODO Clean favorites --> All reset
    // TODO Single item --> Clear or add Favorite
}