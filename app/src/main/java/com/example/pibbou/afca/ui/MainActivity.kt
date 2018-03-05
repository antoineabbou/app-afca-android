package com.example.pibbou.afca.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.FavoriteManager
import com.example.pibbou.afca.ui.fragment.ViewPagerAdapter
import com.example.pibbou.afca.ui.list.EventListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import com.example.pibbou.afca.repository.NotificationManager


class MainActivity : AppCompatActivity() {

    internal lateinit var viewpageradapter: ViewPagerAdapter //Declare PagerAdapter
    internal lateinit var eventlistadapter: EventListAdapter

    var favoriteManager = FavoriteManager()

    private val tabIcons: IntArray = intArrayOf(R.drawable.home, R.drawable.heart_trait, R.drawable.heart_fill)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var notificationManager = NotificationManager(applicationContext)


        favoriteManager.getFavorites(applicationContext)

        viewpageradapter= ViewPagerAdapter(supportFragmentManager)

        Log.i("Info", tabIcons.toString())



        this.viewPager.adapter = viewpageradapter  //Binding PagerAdapter with ViewPager
        this.tab_layout.setupWithViewPager(this.viewPager) //Binding ViewPager with TabLayout
        setupTabIcons() // Put icons inside tab layout --> Need real ones

        val clickListener = View.OnClickListener {view ->
            when (view.getId()) {
                R.id.button -> notificationManager.showNotification(applicationContext,"test", "test")
            }
        }
        button.setOnClickListener(clickListener)
    }

    private fun setupTabIcons() {
        tab_layout.getTabAt(0)?.setIcon(tabIcons[0])
        tab_layout.getTabAt(1)?.setIcon(tabIcons[1])
        tab_layout.getTabAt(2)?.setIcon(tabIcons[2])
    }



}
