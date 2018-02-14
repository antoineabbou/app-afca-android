package com.example.pibbou.afca.ui.fragment

/**
 * Created by antoineabbou on 11/01/2018.
 */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.DataStore
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.list.CategoryListAdapter
import com.example.pibbou.afca.ui.list.FilterListAdapter


class FragmentHome : Fragment() {

    private var view1: View? = null

    // Prepare eventsByDay array
    private var events: ArrayList<Event>? = ArrayList()

    private var recycler_view_category_list: RecyclerView? = null

    private var recycler_view_filter_list: RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view1  = inflater!!.inflate(R.layout.fragment_home, container, false)

        /*rel_main=view?.findViewById<RelativeLayout>(R.id.rel_main) as RelativeLayout
        rel_main?.setBackgroundColor(Color.CYAN)
        tv_name = view?.findViewById<TextView>(R.id.tv_name) as TextView
        tv_name?.text = "Hello"*/

        setupEventsList()
        setupDay()
        setupFilterList()

        return view1
    }

    private fun setupEventsList() {
        // TODO: REMOVE THIS METHOD IN SETUPEVENTSLIST

        // Get recyclerview View
        recycler_view_category_list = view1!!.findViewById<View>(R.id.recycler_view_category_list) as RecyclerView

        // Set fixed size
        recycler_view_category_list!!.setHasFixedSize(true)

        // Prepare adapter
        DataStore.categoriesAdapter = CategoryListAdapter(this.context, DataStore.currentEvents)

        recycler_view_category_list!!.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_category_list!!.adapter = DataStore.categoriesAdapter
    }


    private fun setupFilterList() {
        // TODO: REMOVE THIS METHOD IN SETUPEVENTSLIST

        // Get recyclerview View
        recycler_view_filter_list = view1!!.findViewById<View>(R.id.recycler_view_filter_list) as RecyclerView

        // Set fixed size
        recycler_view_filter_list!!.setHasFixedSize(true)

        // Prepare adapter
        DataStore.filterAdapter = FilterListAdapter(this.context, DataStore.currentFilters)

        recycler_view_filter_list!!.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_filter_list!!.isNestedScrollingEnabled = false
        // Set adapter
        recycler_view_filter_list!!.adapter = DataStore.filterAdapter
    }


    private fun setupDay() {

        val adapter = ArrayAdapter.createFromResource(
                this.context,
                R.array.sort_day,
                android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = view1!!.findViewById<View>(R.id.spinner) as Spinner

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
}