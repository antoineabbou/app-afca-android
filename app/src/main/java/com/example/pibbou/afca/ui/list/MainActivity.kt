package com.example.pibbou.afca.ui.list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.pibbou.afca.R
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
    }


    private fun setupTabIcons() {
        tab_layout.getTabAt(0)?.setIcon(tabIcons[0])
        tab_layout.getTabAt(1)?.setIcon(tabIcons[1])
        tab_layout.getTabAt(2)?.setIcon(tabIcons[2])

    }


}
