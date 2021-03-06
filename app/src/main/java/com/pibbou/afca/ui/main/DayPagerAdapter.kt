package com.pibbou.afca.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

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
        return when (position) {
            0
            -> DayFragment.newInstance(0)
            1
            -> DayFragment.newInstance(1)
            2
            -> DayFragment.newInstance(2)
            3
            -> DayFragment.newInstance(3)
            4
            -> DayFragment.newInstance(4)
            else -> null
        }
    }


    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence {
        return "Jour " + (position + 1)
    }


    companion object {
        private val NUM_ITEMS = 5
    }

}