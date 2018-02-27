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


class MainActivity : AppCompatActivity() {

    internal lateinit var viewpageradapter: ViewPagerAdapter //Declare PagerAdapter
    internal lateinit var eventlistadapter: EventListAdapter
    var favoriteManager = FavoriteManager()

    private val tabIcons = Array(3) {
        R.drawable.home
        R.drawable.home
        R.drawable.home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        favoriteManager.getFavorites(applicationContext)


        viewpageradapter= ViewPagerAdapter(supportFragmentManager)
        eventlistadapter = EventListAdapter(applicationContext, DataStore.events)

        this.viewPager.adapter = viewpageradapter  //Binding PagerAdapter with ViewPager
        this.tab_layout.setupWithViewPager(this.viewPager) //Binding ViewPager with TabLayout
        setupTabIcons() // Put icons inside tab layout --> Need real ones
    }



    public override fun onResume() {
        super.onResume()

        favoriteManager.getFavorites(applicationContext)
        Log.i("onresume", favoriteManager.getFavorites(applicationContext).toString())
        Log.i("favo", favoriteManager.getFavorites(applicationContext).toString())

        this@MainActivity.runOnUiThread(java.lang.Runnable {
            eventlistadapter.notifyDataSetChanged()
            Log.i("something", "yaaaaaaaaa")
        })

    }

    public override fun onRestart() {
        super.onRestart()
        favoriteManager.getFavorites(applicationContext)
        Log.i("onrestart", "true")

    }



    private fun setupTabIcons() {
        tab_layout.getTabAt(0)?.setIcon(tabIcons[0])
        tab_layout.getTabAt(1)?.setIcon(tabIcons[1])
        tab_layout.getTabAt(2)?.setIcon(tabIcons[2])
    }


}
