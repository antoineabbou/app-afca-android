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
import android.widget.Toast
import android.support.v4.view.ViewPager.OnPageChangeListener




class MainActivity : BaseActivity() {

    var adapterViewPager: FragmentPagerAdapter? = null

    // Prepare eventsByDay array
    private lateinit var recycler_view_category_list: RecyclerView
    private lateinit var recycler_view_filter_list: RecyclerView
    private lateinit var mainPager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainPager = findViewById<View>(R.id.mainPager) as ViewPager
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


        this.setupFilterList()

        this.setPagerEvents()
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

    private fun setPagerEvents() {
        // Attach the page change listener inside the activity
        mainPager.addOnPageChangeListener(object : OnPageChangeListener {

            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(position: Int) {
                Toast.makeText(this@MainActivity,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show()
            }
            // This method will be invoked when the current page is scrolled
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


    override fun provideParentLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun setParentLayout(): View {
        return findViewById<View>(R.id.parentPanel) as View
    }

}