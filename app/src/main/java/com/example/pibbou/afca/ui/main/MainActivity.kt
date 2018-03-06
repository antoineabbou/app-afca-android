package com.example.pibbou.afca.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.pibbou.afca.R
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.pibbou.afca.repository.DataStore
import com.example.pibbou.afca.repository.NotificationManager
import com.example.pibbou.afca.ui.base.BaseActivity
import com.example.pibbou.afca.ui.list.CategoryListAdapter
import com.example.pibbou.afca.ui.list.FilterListAdapter
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager


class MainActivity : BaseActivity() {

    var adapterViewPager: FragmentPagerAdapter? = null

    // Prepare eventsByDay array
    private lateinit var recycler_view_category_list: RecyclerView
    private lateinit var recycler_view_filter_list: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainPager = findViewById<View>(R.id.mainPager) as ViewPager
        adapterViewPager = DayPagerAdapter(supportFragmentManager)
        mainPager.adapter = adapterViewPager


        ///////////////
        // Notification
        val notificationManager = NotificationManager(applicationContext)

        val clickListener = View.OnClickListener {view ->
            when (view.getId()) {
                R.id.button -> notificationManager.showNotification(applicationContext,"test", "test")
            }
        }
        button.setOnClickListener(clickListener)


        //////////////
        // Setup
        /*this.setupEventsList()
        this.setupDay()*/

        this.setupFilterList()
    }

    private fun setupEventsList() {

        // Get recyclerview View
        recycler_view_category_list = findViewById<View>(R.id.recycler_view_category_list) as RecyclerView

        // Set fixed size
        recycler_view_category_list.setHasFixedSize(true)

        // Prepare adapter
        DataStore.categoriesAdapter = CategoryListAdapter(applicationContext, DataStore.currentEvents)

        recycler_view_category_list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_category_list.adapter = DataStore.categoriesAdapter
    }

    private fun setupFilterList() {

        // Get recyclerview View
        recycler_view_filter_list = findViewById<View>(R.id.recycler_view_filter_list) as RecyclerView

        // Set fixed size
        recycler_view_filter_list.setHasFixedSize(true)

        // Prepare adapter
        DataStore.filterAdapter = FilterListAdapter(applicationContext, DataStore.currentFilters)

        recycler_view_filter_list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_filter_list.isNestedScrollingEnabled = false
        // Set adapter
        recycler_view_filter_list.adapter = DataStore.filterAdapter

    }

    private fun setupDay() {

        val adapter = ArrayAdapter.createFromResource(
                applicationContext,
                R.array.sort_day,
                android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = findViewById<View>(R.id.spinner) as Spinner

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

    override fun provideParentLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setParentLayout(): View {
        return findViewById<View>(R.id.parentPanel) as View
    }
}