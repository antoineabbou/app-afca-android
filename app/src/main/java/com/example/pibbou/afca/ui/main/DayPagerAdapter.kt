package com.example.pibbou.afca.ui.main

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by arnaudpinot on 06/03/2018.
 */
class DayPagerAdapter(fragmentManager: FragmentManager) : SmartFragmentStatePagerAdapter(fragmentManager) {


    // Returns total number of pages
    override fun getCount(): Int {
        return NUM_ITEMS
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment? {
        when (position) {
            0
            -> return DayFragment.newInstance(0)
            1
            -> return DayFragment.newInstance(1)
            2
            -> return DayFragment.newInstance(2)
            3
            -> return DayFragment.newInstance(3)
            else -> return null
        }
    }


    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence {
        return "Jour " + (position + 1)
    }

    companion object {
        private val NUM_ITEMS = 3
    }

}