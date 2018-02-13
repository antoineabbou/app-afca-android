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


class FragmentHome : Fragment() {

    /*var tv_name: TextView? = null
    var rel_main: RelativeLayout? = null*/
    private var view1: View? = null
    // Prepare eventsByDay array
    private var events: ArrayList<Event>? = ArrayList()

    private var recycler_view_category_list: RecyclerView? = null
    private var categoriesAdapter: CategoryListAdapter? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        view1  = inflater!!.inflate(R.layout.fragment_home, container, false)

        /*rel_main=view?.findViewById<RelativeLayout>(R.id.rel_main) as RelativeLayout
        rel_main?.setBackgroundColor(Color.CYAN)
        tv_name = view?.findViewById<TextView>(R.id.tv_name) as TextView
        tv_name?.text = "Hello"*/


        setupEventsList()
        setupDay()

        return view1
    }

    private fun setupEventsList() {
        // TODO: REMOVE THIS METHOD IN SETUPEVENTSLIST

        // Get recyclerview View
        recycler_view_category_list = view1!!.findViewById<View>(R.id.recycler_view_category_list) as RecyclerView

        // Set fixed size
        recycler_view_category_list!!.setHasFixedSize(true)

        // Prepare adapter
        categoriesAdapter = CategoryListAdapter(this.context, DataStore.currentEvents)

        recycler_view_category_list!!.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_category_list!!.adapter = categoriesAdapter
    }



    private fun setupEventDatas(day: Int) {

        // TODO: https://stackoverflow.com/questions/31367599/how-to-update-recyclerview-adapter-data - UPDATE ADAPTER

        DataStore.currentEvents!!.clear()

        // Thanks to datarepo get all events
        var eventsByDay = DataStore.repository!!.getEventsByDay(day)

        for(event in eventsByDay!!){
            DataStore.currentEvents!!.add(event)
        }
        categoriesAdapter!!.notifyDataSetChanged()
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
                    /*Toast.makeText(this@MainActivity, item.toString(),
                            Toast.LENGTH_SHORT).show()*/
                    // Setup DATAS
                    setupEventDatas(position)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }

    }
}