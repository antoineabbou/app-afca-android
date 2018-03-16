package com.example.pibbou.afca.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.list.CategoryListAdapter


/**
 * Created by arnaudpinot on 06/03/2018.
 */
class DayFragment : android.support.v4.app.Fragment() {

    // Store instance variables
    private var day: Int = 0
    private val repository = App.sInstance.getDataRepository()
    private lateinit var view1: View
    private lateinit var recycler_view_category_list: RecyclerView
    lateinit var categoriesAdapter: CategoryListAdapter
    var eventsByDay : ArrayList<Event>? = ArrayList()

    // Store instance variables based on arguments passed
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get day
        day = arguments.getInt("day", 0)

        // Get Events By Day
        eventsByDay = repository!!.getEventsByDay(day)
    }

    // Inflate the view for the fragment based on layout XML
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        view1 = inflater!!.inflate(R.layout.fragment_day, container, false)

        setupEventsList()

        return view1
    }

    private fun setupEventsList() {

        // Get recyclerview View
        recycler_view_category_list = view1.findViewById<View>(R.id.recycler_view_category_list) as RecyclerView

        // Set fixed size
        recycler_view_category_list.setHasFixedSize(true)

        // Prepare adapter
        categoriesAdapter = CategoryListAdapter(this.context, eventsByDay)

        recycler_view_category_list.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_category_list.adapter = categoriesAdapter
    }

    companion object {

        // newInstance constructor for creating fragment with arguments
        fun newInstance(day: Int): DayFragment? {
            val fragmentFirst = DayFragment()
            val args = Bundle()
            args.putInt("day", day)
            fragmentFirst.setArguments(args)
            return fragmentFirst
        }
    }
}