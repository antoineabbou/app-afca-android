package com.example.pibbou.afca.ui.main

import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.pibbou.afca.R
import android.view.View
import com.example.pibbou.afca.ui.base.BaseActivity
import com.example.pibbou.afca.ui.list.FilterListAdapter
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.transition.TransitionInflater
import android.util.Log
import com.example.pibbou.afca.App
import com.example.pibbou.afca.repository.entity.Event
import com.ogaclejapan.smarttablayout.SmartTabLayout
import android.view.LayoutInflater
import android.widget.*
import android.graphics.Point
import android.support.constraint.ConstraintLayout
import kotlinx.android.synthetic.main.custom_tab.view.*
import android.widget.*


class MainActivity : BaseActivity() {

    private var adapterViewPager: SmartFragmentStatePagerAdapter? = null

    // Prepare eventsByDay array
    private lateinit var recycler_view_filter_list: RecyclerView
    private lateinit var mainPager: ViewPager
    private var filterAdapter: FilterListAdapter? = null
    private var currentFilters: MutableList<Int> = mutableListOf<Int>()
    private val repository = App.sInstance.getDataRepository()
    private var eventsByDay : ArrayList<Event>? = ArrayList()
    private var currentPublic : Int = 0
    private var activityAlreadyStarted : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainPager = findViewById<View>(R.id.mainPager) as ViewPager
        adapterViewPager = DayPagerAdapter(supportFragmentManager)
        mainPager.adapter = adapterViewPager

        val viewPagerTab = findViewById<View>(R.id.viewpagertab) as SmartTabLayout

        // Get event size
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x


        val inflater = LayoutInflater.from(viewPagerTab.context)
        viewPagerTab.setCustomTabView { container, position, adapter ->

            val view = inflater.inflate(R.layout.custom_tab, container,
                    false) as ConstraintLayout

            view.custom_tab_day.minimumWidth = width / 2
            view.custom_tab_day.text = mainPager.adapter.getPageTitle(position)

            when (position) {
                0 -> view.custom_tab_date.text = "Mercredi 04 Avril"
                1 -> view.custom_tab_date.text = "Jeudi 05 Avril"
                2 -> view.custom_tab_date.text = "Vendredi 06 Avril"
                3 -> view.custom_tab_date.text = "Samedi 07 Avril"
            }

            view
        }

        viewPagerTab.setViewPager(mainPager)

        viewPagerTab.setOnTouchListener(View.OnTouchListener { view, motionEvent ->  true})


        this.getEventsAndSetFilters(mainPager.getCurrentItem())
        this.setupFilterList()
        this.setPagerEvents()

        this.setupWindowAnimations()
        this.setNavBarActive()
    }

    private fun setNavBarActive() {
        val button = findViewById<View>(R.id.navBarProg) as ImageButton
        button.isSelected = true
    }

    private fun setupWindowAnimations() {
        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val fade = TransitionInflater.from(this).inflateTransition(R.transition.fade)
            fade.duration = 500
            fade.excludeTarget(R.id.navBar, true)
            // set an exit transition
            window.enterTransition = fade
            window.exitTransition = fade
            window.returnTransition = fade
        } else {
            return
        }
    }


    override fun onResume() {
        super.onResume()

        if (!activityAlreadyStarted) {
            activityAlreadyStarted = true
            return
        }

        this.updateEventDatas(currentPublic)
    }


    private fun setupFilterList() {

        // Get recyclerview View
        recycler_view_filter_list = findViewById<View>(R.id.recycler_view_filter_list) as RecyclerView

        // Set fixed size
        recycler_view_filter_list.setHasFixedSize(true)

        // Prepare adapter
        filterAdapter = FilterListAdapter(this, currentFilters)

        recycler_view_filter_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view_filter_list.isNestedScrollingEnabled = false
        // Set adapter
        recycler_view_filter_list.adapter = filterAdapter

    }


    private fun setPagerEvents() {
        // Attach the page change listener inside the activity
        mainPager.addOnPageChangeListener(object : OnPageChangeListener {

            // This method will be invoked when a new page becomes selected.
            override fun onPageSelected(day: Int) {

                Toast.makeText(this@MainActivity,
                        "Selected page position: " + mainPager.getCurrentItem(), Toast.LENGTH_SHORT).show()

                // Get Events By Day
                getEventsAndSetFilters(day)

            }
            // This method will be invoked when the current page is scrolled
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }


    private fun getEventsAndSetFilters(day: Int) {
        // Get Events By Day
        eventsByDay = repository!!.getEventsByDay(day)
        setFilters(eventsByDay)
    }


    private fun setFilters(eventsByDay: ArrayList<Event>?) {
        currentFilters.clear()

        val numbers: MutableList<Int> = mutableListOf<Int>()
        val numbersFiltered: List<Int>

        for(event in eventsByDay!!){
            numbers.add(event.public!!)
        }
        numbersFiltered = numbers.distinct()

        for(event in numbersFiltered){
            currentFilters.add(event)
        }

        filterAdapter?.notifyDataSetChanged()
    }


    fun updateEventDatas(public: Int) {

        currentPublic = public

        val fragment = adapterViewPager!!.getRegisteredFragment(mainPager.getCurrentItem()) as DayFragment

        fragment.eventsByDay?.clear()

        val eventsByDay = eventsByDay
        val eventList : List<Event>

        if (public != 0) {
            eventList = eventsByDay!!.filter { it.public == public }
        } else {
            eventList = eventsByDay as List<Event>
        }

        for(event in eventList){
            fragment.eventsByDay?.add(event)
        }

        fragment.categoriesAdapter.notifyDataSetChanged()
    }


    override fun provideParentLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun setParentLayout(): View {
        return findViewById<View>(R.id.parentPanel) as View
    }
}