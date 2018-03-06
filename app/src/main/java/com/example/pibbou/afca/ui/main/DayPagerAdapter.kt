package com.example.pibbou.afca.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter



/**
 * Created by arnaudpinot on 06/03/2018.
 */
class DayPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    // Returns total number of pages
    override fun getCount(): Int {
        return NUM_ITEMS
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 // Fragment # 0 - This will show FirstFragment
            -> return DayFragment.newInstance(0)
            1 // Fragment # 0 - This will show FirstFragment different title
            -> return DayFragment.newInstance(1)
            2 // Fragment # 1 - This will show SecondFragment
            -> return DayFragment.newInstance(2)
            else -> return null
        }
    }


    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence {
        return "Page " + position
    }

    companion object {
        private val NUM_ITEMS = 3
    }

}