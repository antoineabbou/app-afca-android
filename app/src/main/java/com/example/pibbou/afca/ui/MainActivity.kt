package com.example.pibbou.afca.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.DataStore
import com.example.pibbou.afca.repository.FavoriteManager
import com.example.pibbou.afca.ui.fragment.ViewPagerAdapter
import com.example.pibbou.afca.ui.list.EventListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.support.v4.app.NotificationCompat
import android.view.View


class MainActivity : AppCompatActivity() {

    internal lateinit var viewpageradapter: ViewPagerAdapter //Declare PagerAdapter
    internal lateinit var eventlistadapter: EventListAdapter
    var favoriteManager = FavoriteManager()

    private val tabIcons: IntArray = intArrayOf(R.drawable.home, R.drawable.heart_trait, R.drawable.heart_fill)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favoriteManager.getFavorites(applicationContext)

        viewpageradapter= ViewPagerAdapter(supportFragmentManager)

        Log.i("Info", tabIcons.toString())



        this.viewPager.adapter = viewpageradapter  //Binding PagerAdapter with ViewPager
        this.tab_layout.setupWithViewPager(this.viewPager) //Binding ViewPager with TabLayout
        setupTabIcons() // Put icons inside tab layout --> Need real ones
    }

    private fun setupTabIcons() {
        tab_layout.getTabAt(0)?.setIcon(tabIcons[0])
        tab_layout.getTabAt(1)?.setIcon(tabIcons[1])
        tab_layout.getTabAt(2)?.setIcon(tabIcons[2])
    }


    fun sendNotification(view: View) {

        //Get an instance of NotificationManager//
        val mBuilder = NotificationCompat.Builder(applicationContext, "com.example.pibbou.afca.ui")
                .setSmallIcon(R.drawable.heart_fill)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        // Gets an instance of the NotificationManager service//
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(1, mBuilder.build())
    }


}
