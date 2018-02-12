package com.example.pibbou.afca.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pibbou.afca.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.pibbou.afca.repository.entity.Event
import android.widget.AdapterView.OnItemSelectedListener
import com.example.pibbou.afca.repository.DataStore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    // Prepare eventsByDay array
    private var events: ArrayList<Event>? = ArrayList()

    private var recycler_view_category_list: RecyclerView? = null
    private var categoriesAdapter: CategoryListAdapter? = null

    internal lateinit var viewpageradapter:ViewPagerAdapter //Declare PagerAdapter
    private val tabIcons = Array(3) {
        R.drawable.home
        R.drawable.home
        R.drawable.home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewpageradapter= ViewPagerAdapter(supportFragmentManager)

        this.viewPager.adapter = viewpageradapter  //Binding PagerAdapter with ViewPager
        this.tab_layout.setupWithViewPager(this.viewPager) //Binding ViewPager with TabLayout
        setupTabIcons() // Put icons inside tab layout --> Need real ones

        setupEventsList()
        setupDay()
    }



    private fun setupEventsList() {
        // TODO: REMOVE THIS METHOD IN SETUPEVENTSLIST

        // Get recyclerview View
        recycler_view_category_list = findViewById<View>(R.id.recycler_view_category_list) as RecyclerView

        // Set fixed size
        recycler_view_category_list!!.setHasFixedSize(true)

        // Prepare adapter
        categoriesAdapter = CategoryListAdapter(this, DataStore.currentEvents)

        recycler_view_category_list!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

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
                    // Setup DATAS
                    setupEventDatas(position)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
                // TODO Auto-generated method stub

            }
        }

    }

    private fun setupTabIcons() {
        tab_layout.getTabAt(0)?.setIcon(tabIcons[0])
        tab_layout.getTabAt(1)?.setIcon(tabIcons[1])
        tab_layout.getTabAt(2)?.setIcon(tabIcons[2])

    }


}
