package com.example.pibbou.afca.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pibbou.afca.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.pibbou.afca.App
import com.example.pibbou.afca.repository.entity.Category
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

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

        this.viewPager.adapter=viewpageradapter  //Binding PagerAdapter with ViewPager
        this.tab_layout.setupWithViewPager(this.viewPager) //Binding ViewPager with TabLayout
        setupTabIcons() // Put icons inside tab layout --> Need real ones


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

    private fun setupTabIcons() {
        tab_layout.getTabAt(0)?.setIcon(tabIcons[0])
        tab_layout.getTabAt(1)?.setIcon(tabIcons[1])
        tab_layout.getTabAt(2)?.setIcon(tabIcons[2])

    }


}
